package com.huiun.integration.repository

import com.huiun.model.User
import com.huiun.model.UserTable
import com.huiun.repository.UserRepository
import com.huiun.repository.UserRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserRepositoryIntegrationTest : BaseRepositoryIntegrationTest() {
    private val defaultUsername = "defaultUser"
    private lateinit var defaultUserId: UUID

    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setupDefaultUser() {
        logger.info("setup default user repository")
        userRepository = UserRepositoryImpl(database)

        transaction(database) {
            SchemaUtils.create(UserTable)

            logger.info("setup default user")
            // Insert a default user and store the ID for later use in tests
            defaultUserId = UserTable.insertAndGetId {
                it[id] = UUID.randomUUID()
                it[email] = "defaultUser"
                it[username] = defaultUsername
                it[passwordHash] = "defaultUser"
                it[createdAt] = CurrentDateTime
                it[updatedAt] = CurrentDateTime
            }.value
        }
    }

    @AfterEach
    fun cleanup() {
        transaction(database) {
            SchemaUtils.drop(UserTable)
            commit()
        }
    }

    @Test
    fun `test create a new user`() = runTest {
        val id = UUID.randomUUID()
        val created = userRepository.create(
            User(
                id = id,
                email = "newuser@gmail.com",
                username = "newuser",
                passwordHash = "newuser"
            )
        )

        assertEquals(id, created.id)
    }

    @Test
    fun `test not allow duplicate username`() = runTest {
        assertThrows<Exception> {
            userRepository.create(
                User(
                    id = UUID.randomUUID(),
                    email = "newusern@gmail.com",
                    username = "defaultUser",
                    passwordHash = "newuser"
                )
            )
        }
    }

    @Test
    fun `test find user by id`() = runTest {
        val user = userRepository.findById(defaultUserId)

        assertNotNull(user)
        assertEquals(defaultUserId, user.id)
    }

    @Test
    fun `test not find user by invalid id`() = runTest {
        val user = userRepository.findById(UUID.randomUUID())

        assertNull(user)
    }

    @Test
    fun `test find user by username`() = runTest {
        val user = userRepository.findByUsername(defaultUsername)

        assertNotNull(user)
        assertEquals(defaultUsername, user.username)
    }

    @Test
    fun `test update user`() = runTest {
        val newPassword = "newPassword"
        val defaultUser = userRepository.findById(
            defaultUserId
        )

        assertNotNull(defaultUser)

        val saved = userRepository.update(User(
            id = defaultUser.id,
            email = defaultUser.email,
            username = defaultUser.username,
            passwordHash = newPassword
        ))

        assertEquals(newPassword, saved.passwordHash)
    }

    @Test
    fun `test delete user`() = runTest {
        userRepository.delete(defaultUserId)
    }
}