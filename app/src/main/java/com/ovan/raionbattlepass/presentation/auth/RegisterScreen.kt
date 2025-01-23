package com.ovan.raionbattlepass.presentation.auth

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.Resource

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = viewModel()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val registerResult by viewModel.registerState.collectAsState(initial = Resource.Loading())
    
    if (registerResult is Resource.Success) {
        navController.navigate("login")
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
        Row {
            Text("Username")
            BasicTextField(value = username.value, onValueChange = { username.value = it })
        }
        Button(onClick = {
            viewModel.register(email.value, password.value, username.value)
            if (registerResult is Resource.Success) {
                navController.navigate("login")
            }
        }) {
            Text("Register")
        }
        TextButton(onClick = {
            navController.navigate("Login")
        }) {
            Text("Login Screen")
        }
        when (registerResult) {
            is Resource.Success -> {
                Text("Login Success: ${registerResult.data}")
            }
            is Resource.Error -> {
                Text(text = "Login Failed: ${registerResult.msg}")
            }
            is Resource.Loading -> {
                Text("Loading")
            }
        }
    }
}