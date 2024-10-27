package com.huiun.routes

import com.huiun.controller.AuthController
import com.huiun.controller.request.UserLoginRequest
import com.huiun.exception.PasswordInvalidException
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authController by application.inject<AuthController>()

    post("api/v1/auth/login") {
        val request = call.receive<UserLoginRequest>()

        try {
            val response = authController.login(request)

            call.respond(HttpStatusCode.OK, response)
        }
        catch (e: PasswordInvalidException) {
            call.respond(HttpStatusCode.Unauthorized)
        }
        catch (e: Exception) {
            call.application.environment.log.error("Unexpected error occur", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}