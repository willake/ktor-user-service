package com.huiun.plugins

import com.huiun.util.JWTTokenProvider
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import org.koin.ktor.ext.get

fun Application.configureSecurity() {

    install(CORS) {
        // this is not safe, but I want to make it easier for demo
        anyHost()
        anyMethod()

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    val tokenProvider = get<JWTTokenProvider>()

    authentication {
        jwt {
            realm = tokenProvider.getRealm()
            verifier(tokenProvider.validator)
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
