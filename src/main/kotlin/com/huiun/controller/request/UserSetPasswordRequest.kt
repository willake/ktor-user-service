package com.huiun.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class UserSetPasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
