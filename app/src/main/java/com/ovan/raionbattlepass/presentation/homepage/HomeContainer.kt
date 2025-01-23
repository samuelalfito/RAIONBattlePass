package com.ovan.raionbattlepass.presentation.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.ovan.raionbattlepass.model.repository.network.response.GetPostsDataItem

@Composable
fun HomeContainer(post: GetPostsDataItem?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(text = post?.id.toString())
            Text(text = post?.userId.toString())
        }
        AsyncImage(
            model = post?.imageUrl,
            contentDescription = "images",
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Text(text = post?.createdAt.toString())
            Text(text = post?.updatedAt.toString())
        }
        Text(text = post?.caption.toString())
    }
}