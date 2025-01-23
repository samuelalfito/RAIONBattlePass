package com.ovan.raionbattlepass.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.data.ChangePasswordRequest
import com.ovan.raionbattlepass.data.TokenData
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.GetPostIdResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetPostsByUserIdResponse
import com.ovan.raionbattlepass.model.repository.network.response.GetUserResponse
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

class ProfileViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val _posts = Channel<Resource<GetPostsByUserIdResponse>>()
    val posts = _posts.receiveAsFlow()
    private val _post = Channel<Resource<GetPostIdResponse>>()
    val post = _post.receiveAsFlow()
    private val _user = Channel<Resource<GetUserResponse>>()
    val user = _user.receiveAsFlow()
    
    private val token: StateFlow<TokenData> =
        dataStoreManager.token.map { token ->
            TokenData(token)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TokenData("Unknown")
        )
    
    fun changePassword(newPassword: String, oldPassword: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().changePassword(
                        "Bearer $token", ChangePasswordRequest(newPassword, oldPassword)
                    )
                    if (response.isSuccessful) emit(Resource.Success("Password changed"))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }
        }
    }
    
    fun getUserPosts() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val currentUser = ApiConfig.getApiService().getCurrentUser("Bearer $token")
                    if (currentUser.isSuccessful) {
                        val response = ApiConfig.getApiService().getPostsByUserId(
                            currentUser.body()!!.data?.id!!.toString()
                        )
                        emit(Resource.Success(response.body()))
                    } else emit(Resource.Error(currentUser.message()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _posts.send(it)
            }
        }
    }
    
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
    
    fun getUser() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().getCurrentUser("Bearer $token")
                    if (!response.isSuccessful) {
                        emit(Resource.Error(msg = response.message()))
                        return@flow
                    }
                    val userResponse = ApiConfig.getApiService().getUser(response.body()?.data?.id!!)
                    emit(Resource.Success(userResponse.body()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _user.send(it)
            }
        }
    }
}