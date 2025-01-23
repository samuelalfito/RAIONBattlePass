package com.ovan.raionbattlepass.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ovan.raionbattlepass.model.repository.network.response.GetPostsByUserIdDataItem
import com.ovan.raionbattlepass.util.Resource

@Composable
fun PostDetailScreen(navController: NavController, id: String) {
    val viewModel: PostDetailViewModel = viewModel()
    val detailedPost by viewModel.post.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.getPostId(id)
    }
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            IconButton(onClick = {
                viewModel.editPost(id, "caption")
            }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = {
                viewModel.deletePost(id)
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
        Row {
            Text(text = detailedPost.data?.data?.id.toString())
            Text(text = detailedPost.data?.data?.userId.toString())
        }
        AsyncImage(
            model = detailedPost.data?.data?.imageUrl,
            contentDescription = "images",
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Text(text = detailedPost.data?.data?.createdAt.toString())
            Text(text = detailedPost.data?.data?.updatedAt.toString())
        }
        Text(text = detailedPost.data?.data?.caption.toString())
    }
}