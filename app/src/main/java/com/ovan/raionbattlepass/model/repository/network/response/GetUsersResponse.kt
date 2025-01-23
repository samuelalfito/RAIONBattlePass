package com.ovan.raionbattlepass.model.repository.network.response

data class GetUsersResponse(
	val data: List<GetUsersDataItem?>? = null,
	val status: String? = null
)

data class GetUsersDataItem(
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null
)

