package com.huiun.service

import com.huiun.repository.UserRepository
import com.huiun.util.JWTTokenProvider

interface AuthService {
    suspend fun authenticateUser(username: String, password: String): String
}

class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JWTTokenProvider
) : AuthService {
    
    override suspend fun authenticateUser(username: String, password: String): String {
        TODO("Not yet implemented")
    }
}