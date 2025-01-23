package com.ovan.raionbattlepass.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.Resource

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchViewModel = viewModel()
    val search = remember { mutableStateOf("") }
    val searchPosts by viewModel.searchPosts.collectAsState(initial = Resource.Loading())
    val searchUsers by viewModel.searchUsers.collectAsState(initial = Resource.Loading())
    
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = search.value,
            onValueChange = { search.value = it },
            label = { Text(text = "Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { viewModel.searchPosts(search.value) }) {
            Text(text = "Search Posts")
        }
        Button(onClick = { viewModel.searchUsers(search.value) }) {
            Text(text = "Search Users")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
        when (searchPosts) {
            is Resource.Loading -> {
                Text(text = "Loading...")
            }
            
            is Resource.Success -> {
                val data = (searchPosts as Resource.Success).data
                data?.searchPostsResponse?.forEach { responseItem ->
                    responseItem?.data?.forEach { post ->
                        Text(text = post?.caption ?: "No caption")
                    }
                }
            }
            
            is Resource.Error -> {
                val msg = (searchPosts as Resource.Error).msg
                Text(text = msg ?: "An error occurred")
            }
        }
        
        when (searchUsers) {
            is Resource.Loading -> {
                Text(text = "Loading...")
            }
            
            is Resource.Success -> {
                val data = (searchUsers as Resource.Success).data
                data?.searchUsersResponse?.forEach { responseItem ->
                    Text(text = responseItem?.username ?: "No username")
                }
            }
            
            is Resource.Error -> {
                val msg = (searchUsers as Resource.Error).msg
                Text(text = msg ?: "An error occurred")
            }
        }
    }
}