package com.huiun.model

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserTable : UUIDTable("public.app_user") {
    val email: Column<String> = varchar("email", 255)
    val username: Column<String> = varchar("username", 50).uniqueIndex()
    val passwordHash: Column<String> = varchar("password_hash", 255)
    val createdAt: Column<LocalDateTime> = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt: Column<LocalDateTime> = datetime("updated_at").defaultExpression(CurrentDateTime)
}