package com.huiun.controller.request

import com.huiun.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserUpdateRequest(
    val id: String,
    val email: String
)
