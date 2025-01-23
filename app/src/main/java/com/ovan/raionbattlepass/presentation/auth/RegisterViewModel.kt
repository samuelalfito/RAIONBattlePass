package com.ovan.raionbattlepass.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.data.GuestRegisterRequest
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.GuestRegisterResponse
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerState = Channel<Resource<GuestRegisterResponse>>()
    val registerState = _registerState.receiveAsFlow()
    
    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response =
                        ApiConfig.getApiService().register(GuestRegisterRequest(email, password, username))
                    if (response.isSuccessful) emit(Resource.Success(response.body()))
                    else emit(Resource.Error(msg = response.message()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _registerState.send(it)
            }
        }
    }
}