package com.huiun.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    val accessToken: String
)
