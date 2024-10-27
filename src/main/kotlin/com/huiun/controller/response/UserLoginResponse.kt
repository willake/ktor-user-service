package com.huiun.controller.response

import com.huiun.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserLoginResponse(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val accessToken: String
)
