package com.huiun.controller.request

import com.huiun.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserUpdateRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val email: String
)
