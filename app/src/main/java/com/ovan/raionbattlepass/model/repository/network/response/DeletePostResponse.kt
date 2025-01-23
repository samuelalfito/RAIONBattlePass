package com.ovan.raionbattlepass.model.repository.network.response

data class DeletePostResponse(
	val data: Data? = null,
	val status: String? = null
)

data class Data(
	val message: String? = null
)

