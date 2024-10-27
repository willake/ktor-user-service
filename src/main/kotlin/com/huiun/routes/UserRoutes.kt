package com.huiun.routes

import com.huiun.controller.UserController
import com.huiun.controller.request.UserCreationRequest
import com.huiun.controller.request.UserSetPasswordRequest
import com.huiun.controller.request.UserUpdateRequest
import io.ktor.http.*
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

    get("api/v1/user/{id}") {
        try {
            val id = call.parameters["id"]?.toString() ?: throw IllegalArgumentException("Invalid ID")

            val response = userController.findUserById(id)
            call.respond(HttpStatusCode.OK, response)
        }
        catch (e: Exception) {
            call.application.environment.log.error("Unexpected error occur", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    put("api/v1/user/{id}/password") {
        try {
            val id = call.parameters["id"]?.toString() ?: throw IllegalArgumentException("Invalid ID")

            val request = call.receive<UserSetPasswordRequest>()
            val response = userController.setPassword(id, request)

            call.respond(HttpStatusCode.OK, response)
        }
        catch (e: Exception) {
            call.application.environment.log.error("Unexpected error occur", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    put("api/v1/user") {
        val request = call.receive<UserUpdateRequest>()

        try {
            val response = userController.updateUserInfo(request)

            call.respond(HttpStatusCode.OK, response)
        }
        catch (e: Exception) {
            call.application.environment.log.error("Unexpected error occur", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    delete("api/v1/user/{id}") {
        val id = call.parameters["id"]?.toString() ?: throw IllegalArgumentException("Invalid ID")

        try {
            userController.deleteUser(id)
        }
        catch (e: Exception) {
            call.application.environment.log.error("Unexpected error occur", e)
            call.respond(HttpStatusCode.InternalServerError)
        }
        TODO("Handle deleting a user")
    }
}