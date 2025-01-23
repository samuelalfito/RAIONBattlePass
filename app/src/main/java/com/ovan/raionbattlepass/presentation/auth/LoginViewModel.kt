package com.ovan.raionbattlepass.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.data.GuestLoginRequest
import com.ovan.raionbattlepass.data.TokenData
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.model.repository.network.response.CurrentUserResponse
import com.ovan.raionbattlepass.model.repository.network.response.GuestLoginResponse
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

class LoginViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val _loginState = Channel<Resource<GuestLoginResponse>>()
    val loginState = _loginState.receiveAsFlow()
    private val _isLoggedIn = Channel<Resource<CurrentUserResponse>>()
    val isLogged = _isLoggedIn.receiveAsFlow()
    
    private val token: StateFlow<TokenData> =
        dataStoreManager.token.map { token ->
            TokenData(token)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TokenData("Unknown")
        )
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response =
                        ApiConfig.getApiService().login(GuestLoginRequest(email, password))
                    
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()))
                        response.body().let {
                            dataStoreManager.setToken(it?.data?.token.toString())
                        }
                    }
                    else emit(Resource.Error(msg = response.message()))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _loginState.send(it)
            }
        }
    }
    
    fun isLoggedIn() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService().getCurrentUser("Bearer $token")
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()))
                    }
                    else emit(Resource.Error(msg = "Token not found"))
                } catch (e: Exception) {
                    emit(Resource.Error(msg = e.message))
                }
            }.collect {
                _isLoggedIn.send(it)
            }
        }
    }
}