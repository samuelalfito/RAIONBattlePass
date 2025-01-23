package com.ovan.raionbattlepass.presentation.friendlistpage

import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.Resource

@Composable
fun FriendListScreen(navController: NavController) {
    val viewModel: FriendListViewModel = viewModel()
    val friendList by viewModel.friendList.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.getFriendList()
    }
    
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item("Friend List") {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
            
            Text("Friend List")
        }
        items(friendList.data?.data ?: emptyList()) { user ->
            Text(user?.username.toString())
        }
    }
}