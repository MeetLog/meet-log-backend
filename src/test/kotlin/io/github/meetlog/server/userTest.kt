package io.github.meetlog.server

import com.zaxxer.hikari.HikariConfig
import io.github.meetlog.server.database.DatabaseRepository
import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

@BeforeTest
fun beforeTest() {
    val config = HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:"
        connectionTestQuery = "VALUES 1"
    }

    DatabaseRepository.init(config)
}

@Test
fun registerUserTest() = withTestApplication {
    val NAME = "TestUser"
    val PASSWORD = "TestUserPassword"
    val ICON_URL = "http://icon-url.com"
    val NFC_IDM = 0L
    with(handleRequest(HttpMethod.Post, "/register") {
        //language=json
        val body = """
                {
                  "name": $NAME,
                  "password": $PASSWORD,
                  "icon_url": $ICON_URL,
                  "nfc_idm": "$NFC_IDM"
                }
            """.trimIndent()
        setBody(body)
    }) {
        assertNotNull(response.content)
    }
    Unit
}