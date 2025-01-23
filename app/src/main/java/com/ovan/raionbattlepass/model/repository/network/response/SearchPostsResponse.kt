package com.ovan.raionbattlepass.model.repository.network.response

data class SearchPostsResponse(
	val searchPostsResponse: List<SearchPostsResponseItem?>? = null
)

data class SearchPostsDataItem(
	val updatedAt: String? = null,
	val userId: String? = null,
	val imageUrl: String? = null,
	val caption: String? = null,
	val createdAt: String? = null,
	val id: String? = null
)

data class SearchPostsResponseItem(
	val data: List<SearchPostsDataItem?>? = null,
	val status: String? = null
)

