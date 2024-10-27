package com.huiun.service

import com.huiun.model.User
import com.huiun.repository.UserRepository
import com.huiun.util.PasswordUtil
import java.util.UUID

interface UserService {
    suspend fun createUser(email: String, username: String, password: String): User
    suspend fun findUserById(id: UUID): User
    suspend fun updateUserInfo(id: UUID, email: String): User
    suspend fun setNewPassword(id: UUID, oldPassword: String, newPassword: String)
    suspend fun deleteUser(id: UUID)
}

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override suspend fun createUser(email: String, username: String, password: String): User {

        // check if username exist
        if(userRepository.findByUsername(username) != null) {
            throw Exception("Username exist")
        }

        return userRepository.create(User(
            UUID.randomUUID(),
            email,
            username,
            PasswordUtil.hashPassword(password)
        ))
    }

    override suspend fun findUserById(id: UUID): User {
        val user = userRepository.findById(id)?: throw Exception("User not found")

        return user
    }

    override suspend fun updateUserInfo(id: UUID, email: String): User {
        val user = userRepository.findById(id) ?: throw Exception("User not found")

        // email is the only editable info currently

        return userRepository.update(User(
            user.id,
            email,
            user.username,
            user.passwordHash
        ))
    }

    override suspend fun setNewPassword(
        id: UUID, oldPassword: String, newPassword: String) {
        val user = userRepository.findById(id) ?: throw Exception("User not found")

        // verify old password
        if(!PasswordUtil.verifyPassword(oldPassword, user.passwordHash)) {
            throw Exception("Wrong old password")
        }

        userRepository.update(User(
            user.id,
            user.email,
            user.username,
            PasswordUtil.hashPassword(newPassword)
        ))
    }

    override suspend fun deleteUser(id: UUID) {
        val user = userRepository.findById(id) ?: throw Exception("User not found")

        userRepository.delete(id)
    }
}