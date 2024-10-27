package com.huiun.plugins

import com.huiun.routes.authRoutes
import com.huiun.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRoutes()
        authRoutes()
    }
}
