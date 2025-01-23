package com.ovan.raionbattlepass.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.data.TokenData
import com.ovan.raionbattlepass.data.UpdatePostRequest
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.GetPostIdResponse
import com.ovan.raionbattlepass.util.DataStoreManager
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PostDetailViewModel(private val dataStoreManager: DataStoreManager): ViewModel() {
    private val _post = Channel<Resource<GetPostIdResponse>>()
    val post = _post.receiveAsFlow()
    
    private val token: StateFlow<TokenData> =
        dataStoreManager.token.map { token ->
            TokenData(token)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TokenData("Unknown")
        )
    
    fun getPostId(postId: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().getPostId(postId)
                    if (response.isSuccessful) emit(Resource.Success(response.body()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _post.send(it)
            }
        }
    }
    
    fun editPost(id: String, caption: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().updatePost("Bearer $token", id, UpdatePostRequest(caption))
                    if (response.isSuccessful) emit(Resource.Success(response.body()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }
        }
    }
    
    fun deletePost(id: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().deletePost("Bearer $token", id)
                    if (response.isSuccessful) emit(Resource.Success(response.body()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }
        }
    }
}