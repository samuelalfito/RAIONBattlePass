package com.ovan.raionbattlepass.data

data class ChangePasswordRequest(
    val newPassword: String,
    val oldPassword: String,
)
