package com.huiun.di

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
}