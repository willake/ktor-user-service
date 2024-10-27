package com.huiun.controller

import com.huiun.controller.request.UserCreationRequest
import com.huiun.controller.request.UserSetPasswordRequest
import com.huiun.controller.response.UserResponse
import com.huiun.model.User
import com.huiun.service.UserService

interface UserController {
    suspend fun createUser(userCreationRequest: UserCreationRequest): UserResponse
    suspend fun findUserById(id: String): UserResponse
    suspend fun updateUserInfo(): UserResponse
    suspend fun setPassword(userSetPasswordRequest: UserSetPasswordRequest)
    suspend fun deleteUser(id: String)
}

class UserControllerImpl(
    private val userService: UserService
) : UserController {
    override suspend fun createUser(userCreationRequest: UserCreationRequest): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun findUserById(id: String): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserInfo(): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun setPassword(userSetPasswordRequest: UserSetPasswordRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: String) {
        TODO("Not yet implemented")
    }


}