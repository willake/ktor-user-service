package com.huiun.integration.repository

import io.ktor.util.logging.*
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeEach

abstract class BaseRepositoryIntegrationTest {

    protected lateinit var database: Database
    val logger = KtorSimpleLogger("com.huiun.integration.repository.BaseRepositoryIntegrationTest")

    // idea reference: https://github.com/mathias21/KtorEasy/blob/master/src/test/kotlin/com.batcuevasoft/dao/BaseDaoTest.kt
    @BeforeEach
    fun setup() {
        logger.info("setup database connection")
        database = Database.connect("jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "org.h2.Driver")
    }
}