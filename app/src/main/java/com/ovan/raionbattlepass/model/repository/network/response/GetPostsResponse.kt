package com.ovan.raionbattlepass.model.repository.network.response

data class GetPostsResponse(
	val data: List<GetPostsDataItem?>? = null,
	val status: String? = null
)

data class GetPostsDataItem(
	val updatedAt: String? = null,
	val userId: String? = null,
	val imageUrl: String? = null,
	val caption: String? = null,
	val createdAt: String? = null,
	val id: String? = null
)

