package com.huiun.plugins

import com.huiun.model.UserTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject

fun Application.configureDatabase() {
    val database by inject<Database>()

    transaction(database) {
        SchemaUtils.create(UserTable)
    }
}