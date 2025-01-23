package com.ovan.raionbattlepass.model.repository.network.response

data class UpdatePostResponse(
	val data: UpdatePostData? = null,
	val status: String? = null
)

data class UpdatePostData(
	val updatedAt: String? = null,
	val userId: String? = null,
	val imageUrl: String? = null,
	val caption: String? = null,
	val createdAt: String? = null,
	val id: String? = null
)

