package io.github.meetlog.server.auth

import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.util.matchPassword
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import kotlinx.serialization.Serializable

@Serializable
data class LoginUser(
    val id: Int,
    val name: String,
    val iconUrl: String?,
    val nfcIdm: Long
)

@Serializable
data class RegisterRequest(
    val name: String,
    val password: String,
    val iconUrl: String?,
    val nfcIdm: Long
)

@Serializable
data class LoginRequest(
    val id: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val user: LoginUser,
    val token: String
)

fun Route.register() {
    post("register") {
        val req = call.receive<RegisterRequest>()
        val id = DatabaseRepository.registerUser(
            name = req.name,
            password = req.password,
            iconUrl = req.iconUrl,
            nfcIdm = req.nfcIdm
        )

        val user = DatabaseRepository.getUser("$id") ?: throw IllegalStateException("What a wrong")
        val res = LoginUser(
            id = user.id.value,
            name = user.name,
            nfcIdm = user.nfcIdm,
            iconUrl = user.iconUrl
        )
        call.respond(res)
    }
}

fun Route.login() {
    post("login") {
        val req = call.receive<LoginRequest>()
        val user = DatabaseRepository.getUser(req.id)

        if (user != null && req.password.matchPassword(user.password)) {
            val token = JwtConfig.makeToken(user)
            val res = LoginUser(
                id = user.id.value,
                name = user.name,
                iconUrl = user.iconUrl,
                nfcIdm = user.nfcIdm
            )
            call.respond(LoginResponse(res, token))
        } else {
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}