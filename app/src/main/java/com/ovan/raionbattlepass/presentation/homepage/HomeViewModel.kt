package com.ovan.raionbattlepass.presentation.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.GetPostsResponse
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _posts = Channel<Resource<GetPostsResponse>>()
    val posts = _posts.receiveAsFlow()
    
    fun getPosts() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().getPosts()
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()!!))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } catch (e: Exception) {
                    _posts.send(Resource.Error(e.message.toString()))
                }
            }
        }
    }
}