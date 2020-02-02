package io.github.meetlog.server.auth

import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.database.entity.UserEntity
import io.github.meetlog.server.util.matchPassword
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.login() {
    post("login") {
        val req = call.receive<LoginRequest>()
        val user = DatabaseRepository.getUser(req.id)

        if (user != null && req.password.matchPassword(user.password)) {
            val token = JwtConfig.makeToken(user)
            call.respond(LoginResponse(user, token))
        } else {
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}

data class LoginRequest(
    val id: String,
    val password: String
)

data class LoginResponse(
    val user: UserEntity,
    val token: String
)