package com.huiun.unit.service

import com.huiun.model.User
import com.huiun.repository.UserRepository
import com.huiun.repository.UserRepositoryImpl
import com.huiun.service.UserServiceImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.assertEquals

class UserServiceUnitTest {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserServiceImpl(userRepository)

    private val defaultUser = User(
        UUID.randomUUID(),
        "defaultUser@gmail.com",
        "defaultUser",
        "defaultPassword"
    )

    @Test
    fun `test create a new user`() = runTest {
        val email = "newUser@gmail.com"
        val username = "newUser"
        val password = "newPassword"

        coEvery { userRepository.create(any()) } returns User(
            UUID.randomUUID(), email, username, password)
        coEvery { userRepository.findByUsername(username) } returns null

        val user = userService.createUser(email, username, password)

        assertEquals(email, user.email)
    }

    @Test
    fun `test not create with a exist username`() = runTest {
        val userId = UUID.randomUUID()
        val email = "newUser@gmail.com"
        val username = "newUser"
        val password = "newPassword"

        coEvery { userRepository.create(any()) } returns User(
            userId, email, username, password)
        coEvery { userRepository.findByUsername(username) } returns User(
            userId, email, username, password)

        assertThrows<Exception> {
            val user = userService.createUser(email, username, password)
        }
    }
}