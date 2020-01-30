package io.github.meetlog.server.config

data class DatabaseConfig(
    val user: String,
    val password: String,
    val host: String,
    val port: Int,
    val dataBaseName: String
)