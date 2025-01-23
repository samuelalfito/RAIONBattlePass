package com.ovan.raionbattlepass.model.repository.network.response

data class GetPostsByUserIdResponse(
	val data: List<GetPostsByUserIdDataItem?>? = null,
	val status: String? = null
)

data class GetPostsByUserIdDataItem(
	val updatedAt: String? = null,
	val userId: String? = null,
	val imageUrl: String? = null,
	val caption: String? = null,
	val createdAt: String? = null,
	val id: String? = null
)

