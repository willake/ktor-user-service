package com.huiun.routes

import io.ktor.server.routing.*

fun Route.userRoutes() {

    post("api/v1/user") {
        TODO("Handle creating users")
    }

    get("api/v1/user/{id}") {
        TODO("Handle getting a user by id")
    }

    put("api/v1/user/{id}/password") {
        TODO("Handle setting a new password")
    }

    put("api/v1/user") {
        TODO("Handle setting user information except password")
    }

    delete("api/v1/user/{id}") {
        TODO("Handle deleting a user")
    }
}