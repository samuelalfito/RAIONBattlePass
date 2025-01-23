package com.ovan.raionbattlepass.model.repository.network.response

data class GuestRegisterResponse(
	val data: GuestRegisterData? = null,
	val status: String? = null
)

data class GuestRegisterData(
	val message: String? = null
)

