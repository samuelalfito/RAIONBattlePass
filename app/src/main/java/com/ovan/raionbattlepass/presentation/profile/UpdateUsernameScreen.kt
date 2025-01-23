package com.ovan.raionbattlepass.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.DataStoreManager

@Composable
fun UpdateUsernameScreen(navController: NavController, dataStoreManager: DataStoreManager) {
    val viewModel = UpdateUsernameViewModel(dataStoreManager)
    val username = remember { mutableStateOf("") }
    
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") })
        
        Button(onClick = {
            viewModel.updateUsername(username = username.value)
        }) {
            Text(text = "Update Username")
        }
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}