package io.github.meetlog.server

import com.zaxxer.hikari.HikariConfig
import io.github.meetlog.server.auth.JwtConfig
import io.github.meetlog.server.auth.login
import io.github.meetlog.server.auth.register
import io.github.meetlog.server.config.DatabaseConfig
import io.github.meetlog.server.config.loadConfig
import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.graphql.formatErrorGraphQlError
import io.github.meetlog.server.graphql.graphQlSchema
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ktor.graphql.config
import ktor.graphql.graphQL

fun dbConfig(): HikariConfig {
    val config = loadConfig<DatabaseConfig>("dbConfig.yml")
    return HikariConfig().apply {
        driverClassName = "com.mysql.cj.jdbc.Driver"
        jdbcUrl = "jdbc:mysql://${config.host}:${config.port}/${config.dataBaseName}?serverTimezone=JST"
        username = config.user
        password = config.password

        addDataSourceProperty("cachePrepStmts", "true")
        addDataSourceProperty("prepStmtCacheSize", "250")
        addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    }
}

fun main() {
    DatabaseRepository.init(dbConfig())

    val server = embeddedServer(Netty, 8080) {
        install(CallLogging)
        install(ContentNegotiation) {
            serialization()
        }

        authentication {
            jwt {
                verifier(JwtConfig.verifier)
                realm = "meetlog.github.io"
                validate {
                    it.payload.getClaim("id")
                        .asString()
                        ?.let(DatabaseRepository::getUser)
                }
            }
        }

        routing {
            login()
            register()

            // authenticateブロック内はトークンによる認証が必要
            authenticate {
                graphQL("graphql", graphQlSchema) {
                    config {
                        // graphiql = true
                        this.formatError = formatErrorGraphQlError
                    }
                }
            }
        }
    }

    server.start(wait = true)
}