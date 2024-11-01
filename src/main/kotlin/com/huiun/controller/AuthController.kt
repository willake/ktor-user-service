package com.huiun.controller

import com.huiun.controller.request.UserLoginRequest
import com.huiun.controller.response.UserLoginResponse
import com.huiun.service.AuthService

interface AuthController {
    suspend fun login(userLoginRequest: UserLoginRequest): UserLoginResponse
}

class AuthControllerImpl(
    private val authService: AuthService
) : AuthController {
    override suspend fun login(userLoginRequest: UserLoginRequest): UserLoginResponse {

        val result = authService.authenticateUser(
            userLoginRequest.username,
            userLoginRequest.password
        )

        return UserLoginResponse(result.first, result.second)
    }
}