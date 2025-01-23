package com.ovan.raionbattlepass.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.SearchPostsResponse
import com.ovan.raionbattlepass.model.repository.network.response.SearchUsersResponse
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val _searchPosts = Channel<Resource<SearchPostsResponse>>()
    val searchPosts = _searchPosts.receiveAsFlow()
    private val _searchUsers = Channel<Resource<SearchUsersResponse>>()
    val searchUsers = _searchUsers.receiveAsFlow()
    
    fun searchPosts(query: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().searchPosts(query)
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _searchPosts.send(it)
            }
        }
    }
    
    fun searchUsers(query: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().searchUsers(query)
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _searchUsers.send(it)
            }
        }
    }
}