package com.huiun.integration.repository

import com.huiun.model.UserTable
import com.huiun.repository.UserRepository
import com.huiun.repository.UserRepositoryImpl
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.util.UUID

class UserRepositoryIntegrationTest : BaseRepositoryIntegrationTest() {

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
                it[username] = "defaultUser"
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
}