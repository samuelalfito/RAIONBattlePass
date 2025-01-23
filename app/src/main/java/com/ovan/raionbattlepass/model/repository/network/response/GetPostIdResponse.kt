package com.ovan.raionbattlepass.model.repository.network.response

data class GetPostIdResponse(
	val data: GetPostDetailData? = null,
	val status: String? = null
)

data class GetPostDetailData(
	val updatedAt: String? = null,
	val userId: String? = null,
	val imageUrl: String? = null,
	val caption: String? = null,
	val createdAt: String? = null,
	val id: String? = null
)

