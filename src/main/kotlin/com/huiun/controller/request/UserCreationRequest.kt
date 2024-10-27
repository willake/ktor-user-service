package com.huiun.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class UserCreationRequest(
    val email: String,
    val username: String,
    val password: String
)
