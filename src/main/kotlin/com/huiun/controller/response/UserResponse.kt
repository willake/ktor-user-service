package com.huiun.controller.response

import com.huiun.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val email: String,
    val username: String
)
