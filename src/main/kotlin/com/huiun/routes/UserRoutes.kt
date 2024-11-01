package com.huiun.routes

import com.huiun.controller.UserController
import com.huiun.controller.request.UserCreationRequest
import com.huiun.controller.request.UserSetPasswordRequest
import com.huiun.controller.request.UserUpdateRequest
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {

    val userController by application.inject<UserController>()

    post("api/v1/user") {
        val request = call.receive<UserCreationRequest>()

        try {
            val response = userController.createUser(request)

            call.respond(HttpStatusCode.Created, response)
        }
        catch (e: Exception) {
            call.application.environment.log.error("Unexpected error occur", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    authenticate {
        get("api/v1/user/{id}") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
                val principal = call.principal<JWTPrincipal>()

                if(principal != null && principal.subject != id) {
                    call.respond(HttpStatusCode.Forbidden)
                }

                val response = userController.findUserById(id)
                call.respond(HttpStatusCode.OK, response)

            } catch (e: Exception) {
                call.application.environment.log.error("Unexpected error occur", e)
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        put("api/v1/user/{id}/password") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
                val principal = call.principal<JWTPrincipal>()

                if(principal != null && principal.subject != id) {
                    call.respond(HttpStatusCode.Forbidden)
                }

                val request = call.receive<UserSetPasswordRequest>()
                val response = userController.setPassword(id, request)

                call.respond(HttpStatusCode.OK, response)
            } catch (e: Exception) {
                call.application.environment.log.error("Unexpected error occur", e)
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        put("api/v1/user") {
            val request = call.receive<UserUpdateRequest>()
            val principal = call.principal<JWTPrincipal>()

            if(principal != null && principal.subject != request.id) {
                call.respond(HttpStatusCode.Forbidden)
            }

            try {
                val response = userController.updateUserInfo(request)

                call.respond(HttpStatusCode.OK, response)
            } catch (e: Exception) {
                call.application.environment.log.error("Unexpected error occur", e)
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        delete("api/v1/user/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("Invalid ID")
            val principal = call.principal<JWTPrincipal>()

            if(principal != null && principal.subject != id) {
                call.respond(HttpStatusCode.Forbidden)
            }

            try {
                userController.deleteUser(id)

                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                call.application.environment.log.error("Unexpected error occur", e)
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}