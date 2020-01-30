package io.github.meetlog.server.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.github.meetlog.server.database.entity.User
import java.util.Date
import java.util.concurrent.TimeUnit

object JwtConfig {
    private const val secret = "4!M3DwB8s%4dC1*g"
    private const val issuer = "meetlog.github.io"
    private val validity = TimeUnit.HOURS.toMillis(10)
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    fun makeToken(user: User): String  = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withExpiresAt(getExpiration())
        .withClaim("id", user.name)
        .withClaim("nfcIdm", user.nfcIdm)
        .withClaim("iconUrl", user.iconUrl)
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validity)
}