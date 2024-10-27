package com.huiun.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.huiun.model.User
import java.util.*

class JWTTokenProvider(
    private val secretKey: String,
    private val expiration: Long,
    private val issuer: String,
    private val audience: String,
    private val myRealm: String
) {

    val validator = JWT
        .require(Algorithm.HMAC256(secretKey))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun getRealm(): String = myRealm

    fun generateToken(user: User): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject(user.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + expiration))
            .sign(Algorithm.HMAC256(secretKey))
    }
}