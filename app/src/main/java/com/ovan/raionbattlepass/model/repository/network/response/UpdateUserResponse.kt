package com.ovan.raionbattlepass.model.repository.network.response

data class UpdateUserResponse(
	val data: UpdateUserData? = null,
	val status: String? = null
)

data class UpdateUserData(
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null
)

