package com.ovan.raionbattlepass.model.repository.network.response

data class CreatePostResponse(
	val data: CreatePostData? = null,
	val status: String? = null
)

data class CreatePostData(
	val updatedAt: String? = null,
	val userId: String? = null,
	val imageUrl: String? = null,
	val caption: String? = null,
	val createdAt: String? = null,
	val id: String? = null
)

