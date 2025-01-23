package com.ovan.raionbattlepass.model.repository.network.response

data class SearchUsersResponse(
	val searchUsersResponse: List<SearchUsersResponseItem?>? = null
)

data class SearchUsersResponseItem(
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null
)

