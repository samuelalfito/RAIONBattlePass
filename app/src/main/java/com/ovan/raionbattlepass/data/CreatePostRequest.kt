package com.ovan.raionbattlepass.data

import java.io.File

data class CreatePostRequest(
    val caption: String,
    val image: File? = null,
)
