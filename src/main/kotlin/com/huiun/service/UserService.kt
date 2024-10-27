package com.huiun.service

import com.huiun.model.User
import com.huiun.repository.UserRepository
import java.util.UUID

interface UserService {
    suspend fun createUser(user: User): User
    suspend fun findUserById(id: UUID): User?
    suspend fun updateUser(user:User): User
    suspend fun setNewPassword(id: UUID, oldPassword: String, newPassword: String)
    suspend fun deleteUser(id: UUID)
}

abstract class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override suspend fun createUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun findUserById(id: UUID): User? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun setNewPassword(
        id: UUID, oldPassword: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: UUID) {
        TODO("Not yet implemented")
    }
}