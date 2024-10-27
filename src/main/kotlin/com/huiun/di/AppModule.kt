package com.huiun.di

import com.huiun.controller.UserController
import com.huiun.controller.UserControllerImpl
import com.huiun.repository.UserRepository
import com.huiun.repository.UserRepositoryImpl
import com.huiun.service.UserService
import com.huiun.service.UserServiceImpl
import com.huiun.util.JWTTokenProvider
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val appModule = module {
    single<Database> {
        val dotenv = dotenv()

        // For easier setup
        // Database.connect(
        // "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "org.h2.Driver")

        // Production
        Database.connect(
            url = dotenv["DB_URL"],
            driver = dotenv["DB_DRIVER"],
            user = dotenv["DB_USERNAME"],
            password = dotenv["DB_PASSWORD"]
        )
    }
    single { JWTTokenProvider(
        dotenv()["JWT_SECRET_KEY"],
        dotenv()["JWT_EXPIRATION"].toLong(),
        dotenv()["JWT_ISSUER"],
        dotenv()["JWT_AUDIENCE"],
        dotenv()["JWT_REALM"]
    ) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserService> { UserServiceImpl(get())}
    single<UserController> { UserControllerImpl(get()) }
}