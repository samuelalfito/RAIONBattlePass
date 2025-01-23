package com.ovan.raionbattlepass.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.data.UpdatePostRequest
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.GetPostIdResponse
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PostDetailViewModel: ViewModel() {
    private val _post = Channel<Resource<GetPostIdResponse>>()
    val post = _post.receiveAsFlow()
    
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
                    val response = ApiConfig.getApiService().updatePost(id, UpdatePostRequest(caption))
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
                    val response = ApiConfig.getApiService().deletePost(id)
                    if (response.isSuccessful) emit(Resource.Success(response.body()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }
        }
    }
}