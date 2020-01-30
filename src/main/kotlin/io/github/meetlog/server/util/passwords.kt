package io.github.meetlog.server.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
// import org.springframework.security.crypto.factory.PasswordEncoderFactories

// private val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
private val encoder = BCryptPasswordEncoder()

fun String.encode(): String = encoder.encode(this)
fun String.matchPassword(encodedPassword: String) = encoder.matches(this, encodedPassword)