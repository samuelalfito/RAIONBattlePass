package com.ovan.raionbattlepass.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.DataStoreManager
import com.ovan.raionbattlepass.util.Resource

@Composable
fun ProfileScreen(navController: NavController, dataStoreManager: DataStoreManager) {
    val viewModel = ProfileViewModel(dataStoreManager)
    val oldPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val posts by viewModel.posts.collectAsState(initial = Resource.Loading())
    val detailedPost by viewModel.post.collectAsState(initial = Resource.Loading())
    val user by viewModel.user.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.getUserPosts()
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(value = oldPassword.value,
            onValueChange = { oldPassword.value = it },
            label = { Text(text = "Old Password") })
        
        OutlinedTextField(value = newPassword.value,
            onValueChange = { newPassword.value = it },
            label = { Text(text = "New Password") })
        
        Row {
            Text(user.data?.data?.id.toString())
            Text(user.data?.data?.email.toString())
            Text(user.data?.data?.username.toString())
            Text(user.data?.data?.createdAt.toString())
            Text(user.data?.data?.updatedAt.toString())
        }
        
        Row {
            Button(onClick = { navController.navigate("friendlist") }) {
                Text(text = "Friend List")
            }
            Button(onClick = { navController.navigate("updateusername") }) {
                Text(text = "Update Username")
            }
        }
        Button(onClick = {
            viewModel.changePassword(
                newPassword = newPassword.value, oldPassword = oldPassword.value
            )
        }) {
            Text(text = "Change Password")
        }
        LazyColumn {
            items(posts.data?.data ?: emptyList()) { post ->
                Text(
                    text = post?.caption.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            viewModel.getPostId(post?.id.toString())
                            if (detailedPost is Resource.Success) navController.navigate("post/${post?.id}")
                        })
                )
            }
        }
    }
}