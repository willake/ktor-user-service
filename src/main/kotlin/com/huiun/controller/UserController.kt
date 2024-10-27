package com.huiun.controller

import com.huiun.controller.request.UserCreationRequest
import com.huiun.controller.request.UserSetPasswordRequest
import com.huiun.controller.request.UserUpdateRequest
import com.huiun.controller.response.UserResponse
import com.huiun.model.User
import com.huiun.service.UserService
import java.util.*

interface UserController {
    suspend fun createUser(userCreationRequest: UserCreationRequest): UserResponse
    suspend fun findUserById(id: String): UserResponse
    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): UserResponse
    suspend fun setPassword(id: String, userSetPasswordRequest: UserSetPasswordRequest)
    suspend fun deleteUser(id: String)
}

class UserControllerImpl(
    private val userService: UserService
) : UserController {
    override suspend fun createUser(userCreationRequest: UserCreationRequest): UserResponse {
        val user = userService.createUser(
            userCreationRequest.email,
            userCreationRequest.username,
            userCreationRequest.password
        )

        return UserResponse(user.id, user.email, user.username)
    }

    override suspend fun findUserById(id: String): UserResponse {
        val user = userService.findUserById(UUID.fromString(id))

        return UserResponse(user.id, user.email, user.username)
    }

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): UserResponse {
        val user = userService.updateUserInfo(
            UUID.fromString(userUpdateRequest.id),
            userUpdateRequest.email)

        return UserResponse(user.id, user.email, user.username)
    }

    override suspend fun setPassword(id: String, userSetPasswordRequest: UserSetPasswordRequest) {
        userService.setNewPassword(
            UUID.fromString(id),
            userSetPasswordRequest.oldPassword, userSetPasswordRequest.newPassword)
    }

    override suspend fun deleteUser(id: String) {
        userService.deleteUser(UUID.fromString(id))
    }


}