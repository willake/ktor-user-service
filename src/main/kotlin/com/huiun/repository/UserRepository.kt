package com.huiun.repository

import com.huiun.model.User
import com.huiun.model.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.UUID

interface UserRepository {
    suspend fun findById(id: UUID): User?
    suspend fun findByUsername(username: String): User?
    suspend fun create(user: User): User
    suspend fun update(user: User): User
    suspend fun delete(id: UUID): Int
}

class UserRepositoryImpl(
    private val database: Database
) : UserRepository {
    override suspend fun findById(id: UUID): User? = transaction(database) {
        UserTable.selectAll()
            .where{ UserTable.id eq id }
            .map { User(
                it[UserTable.id].value,
                it[UserTable.email],
                it[UserTable.username],
                it[UserTable.passwordHash]) }
            .singleOrNull()
    }

    override suspend fun findByUsername(username: String): User? = transaction(database) {
        UserTable.selectAll()
            .where{ UserTable.username eq username }
            .map { User(
                it[UserTable.id].value,
                it[UserTable.email],
                it[UserTable.username],
                it[UserTable.passwordHash]) }
            .singleOrNull()
    }

    override suspend fun create(user: User): User = transaction(database) {
        val id = UserTable.insert {
            it[id] = user.id
            it[email] = user.email
            it[username] = user.username
            it[passwordHash] = user.passwordHash
            it[createdAt] = CurrentDateTime
            it[updatedAt] = CurrentDateTime
        }[UserTable.id].value

        UserTable.selectAll()
            .where{ UserTable.id eq id }
            .map { User(
                it[UserTable.id].value,
                it[UserTable.email],
                it[UserTable.username],
                it[UserTable.passwordHash]) }
            .single()
    }

    override suspend fun update(user: User): User = transaction(database) {
        UserTable.update({ UserTable.id eq user.id }) {
            it[id] = user.id
            it[email] = user.email
            it[username] = user.username
            it[passwordHash] = user.passwordHash
            it[updatedAt] = CurrentDateTime
        }

        UserTable.selectAll()
            .where { UserTable.id eq user.id }
            .map { User(
                it[UserTable.id].value,
                it[UserTable.email],
                it[UserTable.username],
                it[UserTable.passwordHash]) }
            .single()
    }

    override suspend fun delete(id: UUID): Int = transaction(database) {
        UserTable.deleteWhere { UserTable.id eq id }
    }

}