package com.huiun.repository

import com.huiun.model.User
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinComponent
import java.util.UUID

interface UserRepository {
    suspend fun findById(id: UUID): User?
    suspend fun findByUsername(username: String): User?
    suspend fun create(user: User): User
    suspend fun update(user: User): User
    suspend fun delete(id: UUID)
}

class UserRepositoryImpl(
    private val database: Database
) : UserRepository {
    override suspend fun findById(id: UUID): User? {
        TODO("Not yet implemented")
    }

    override suspend fun findByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun create(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun update(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

}