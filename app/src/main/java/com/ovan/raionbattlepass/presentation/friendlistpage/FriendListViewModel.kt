package com.ovan.raionbattlepass.presentation.friendlistpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.GetUsersResponse
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FriendListViewModel : ViewModel() {
    private val _friendList = Channel<Resource<GetUsersResponse>>()
    val friendList = _friendList.receiveAsFlow()
    
    fun getFriendList() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().getUsers()
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage))
                }
            }.collect {
                _friendList.send(it)
            }
        }
    }
}