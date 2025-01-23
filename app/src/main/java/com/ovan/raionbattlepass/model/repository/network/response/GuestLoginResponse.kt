package com.ovan.raionbattlepass.model.repository.network.response

data class GuestLoginResponse(
	val data: GuestLoginData? = null,
	val status: String? = null
)

data class GuestLoginData(
	val token: String? = null
)

