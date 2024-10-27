package com.huiun.service

import com.huiun.exception.PasswordInvalidException
import com.huiun.repository.UserRepository
import com.huiun.util.JWTTokenProvider
import com.huiun.util.PasswordUtil
import java.util.UUID

interface AuthService {
    suspend fun authenticateUser(username: String, password: String):  Pair<UUID, String>
}

class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JWTTokenProvider
) : AuthService {

    override suspend fun authenticateUser(username: String, password: String): Pair<UUID, String> {
        val user = userRepository.findByUsername(username) ?: throw Exception("User not found")

        if(!PasswordUtil.verifyPassword(password, user.passwordHash))
            throw PasswordInvalidException()

        return Pair(user.id, jwtTokenProvider.generateToken(user))
    }
}