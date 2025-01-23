package com.ovan.raionbattlepass.model.repository.network.response

data class GetUserResponse(
	val data: GetUserData? = null,
	val status: String? = null
)

data class GetUserData(
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null
)

