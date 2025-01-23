package com.ovan.raionbattlepass.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovan.raionbattlepass.data.TokenData
import com.ovan.raionbattlepass.data.UpdateUserRequest
import com.ovan.raionbattlepass.model.repository.network.ApiConfig
import com.ovan.raionbattlepass.util.DataStoreManager
import com.ovan.raionbattlepass.util.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UpdateUsernameViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val token: StateFlow<TokenData> =
        dataStoreManager.token.map { token ->
            TokenData(token)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TokenData("Unknown")
        )
    
    fun updateUsername(username: String) {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                try {
                    val response = ApiConfig.getApiService()
                        .updateUser("Bearer $token", UpdateUserRequest(username))
                    if (response.isSuccessful) {
                        emit(Resource.Success(response.body()))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                }
            }
        }
    }
}