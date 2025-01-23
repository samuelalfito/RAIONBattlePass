package com.ovan.raionbattlepass.presentation.homepage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ovan.raionbattlepass.util.Resource

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val posts = viewModel.posts.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.getPosts()
    }
    
    LazyColumn {
        items(posts.value.data?.data ?: emptyList()) { post ->
            HomeContainer(post = post)
        }
    }
}