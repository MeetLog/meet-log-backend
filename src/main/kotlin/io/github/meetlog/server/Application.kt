package io.github.meetlog.server

import io.github.meetlog.server.auth.JwtConfig
import io.github.meetlog.server.config.DatabaseConfig
import io.github.meetlog.server.config.loadConfig
import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.graphql.graphQlSchema
import io.github.meetlog.server.route.login
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ktor.graphql.config
import ktor.graphql.graphQL

fun main() {
    val databaseConfig = loadConfig<DatabaseConfig>("dbConfig.yml")
    DatabaseRepository.init(databaseConfig)

    val server = embeddedServer(Netty, 8080) {
        install(CallLogging)
        install(ContentNegotiation) { /* serialization */ }

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

            // authenticateブロック内はトークンによる認証が必要
            authenticate {
                graphQL("graphql", graphQlSchema) {
                    config {
                        // graphiql = true
                    }
                }
            }
        }
    }

    server.start(wait = true)
}