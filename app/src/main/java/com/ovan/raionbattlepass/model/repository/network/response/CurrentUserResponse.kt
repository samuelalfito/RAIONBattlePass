package com.ovan.raionbattlepass.model.repository.network.response

data class CurrentUserResponse(
	val data: CurrentUserData? = null,
	val status: String? = null
)

data class CurrentUserData(
	val updatedAt: String? = null,
	val createdAt: String? = null,
	val id: String? = null,
	val email: String? = null,
	val username: String? = null
)

