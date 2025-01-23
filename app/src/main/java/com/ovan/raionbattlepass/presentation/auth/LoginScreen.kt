package com.ovan.raionbattlepass.presentation.auth

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.DataStoreManager
import com.ovan.raionbattlepass.util.Resource

@Composable
fun LoginScreen(navController: NavController, dataStoreManager: DataStoreManager) {
    val viewModel = LoginViewModel(dataStoreManager)
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginResult by viewModel.loginState.collectAsState(initial = Resource.Loading())
    val isLoggedIn by viewModel.isLogged.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.isLoggedIn()
        if (isLoggedIn is Resource.Success) {
            navController.navigate("home")
        }
    }
    
    if (loginResult is Resource.Success) {
        navController.navigate("home")
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row {
            Text("Email")
            BasicTextField(value = email.value, onValueChange = { email.value = it })
        }
        Row {
            Text("Password")
            BasicTextField(value = password.value, onValueChange = { password.value = it })
        }
        Button(onClick = {
            viewModel.login(email.value, password.value)
        }) {
            Text("Login")
        }
        TextButton(onClick = {
            navController.navigate("register")
        }) {
            Text("Register Screen")
        }
        when (loginResult) {
            is Resource.Success -> {
                Text("Login Success: ${loginResult.data}")
            }
            is Resource.Error -> {
                Text(text = "Login Failed: ${loginResult.msg}")
            }
            is Resource.Loading -> {
                Text("Loading")
            }
        }
    }
}