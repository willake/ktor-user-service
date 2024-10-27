package com.huiun

import com.huiun.di.appModule
import com.huiun.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    // @Huiun: koin is a dependency injection DSL
    // https://insert-koin.io/docs/reference/koin-ktor/ktor
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    configureDatabase()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
