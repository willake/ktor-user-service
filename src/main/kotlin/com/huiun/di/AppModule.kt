package com.huiun.di

import com.huiun.controller.AuthController
import com.huiun.controller.AuthControllerImpl
import com.huiun.controller.UserController
import com.huiun.controller.UserControllerImpl
import com.huiun.repository.UserRepository
import com.huiun.repository.UserRepositoryImpl
import com.huiun.service.AuthService
import com.huiun.service.AuthServiceImpl
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
        secretKey =  dotenv()["JWT_SECRET_KEY"],
        expiration =  dotenv()["JWT_EXPIRATION"].toLong(),
        issuer =  dotenv()["JWT_ISSUER"],
        audience =  dotenv()["JWT_AUDIENCE"],
        myRealm =  dotenv()["JWT_REALM"]
    ) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserService> { UserServiceImpl(get())}
    single<UserController> { UserControllerImpl(get()) }
    single<AuthService> { AuthServiceImpl(get(), get())}
    single<AuthController> { AuthControllerImpl(get())}
}