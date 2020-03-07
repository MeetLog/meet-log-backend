package io.github.meetlog.server.graphql

import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private val parser = SchemaParser()

fun schemaDef(path: Path): TypeDefinitionRegistry = parser.parse(Files.newBufferedReader(path))

val runtimeWiring = RuntimeWiring {
    val handler = GraphQlHandler()

    type("Query") {
        dataFetcher("getSession") {
            val myId: String by arguments
            val friendsId: String by arguments
            val meetSessionId: String by arguments

            handler.getSession(myId, friendsId, meetSessionId)
        }
    }
    type("Mutation") {
        dataFetcher("removeAccount") {
            val userId: String by arguments
            val accountId: String by arguments

            handler.removeAccount(userId, accountId)
        }
        dataFetcher("createGroupSession") {
            val myId: String by arguments
            val date: String by arguments
            val place: String? by arguments

            handler.createGroupSession(myId, date, place)
        }
        dataFetcher("connectAccount") {
            val myId: String by arguments
            val friendsId: String by arguments
            val meetSession: String by arguments

            handler.connectAccount(myId, friendsId, meetSession)
        }
        dataFetcher("addAccount") {
            val userId: String by arguments
            val snsType: String by arguments
            val snsId: String by arguments

            handler.addAccount(userId, snsType, snsId)
        }
        dataFetcher("postLog") {
            val sessionId: String by arguments
            val comments: String? by arguments
            val images: List<String>? by arguments

            handler.postLog(sessionId, comments, images)
        }
        dataFetcher("leaveMeetSession") {
            val meetSessionId: String by arguments
            val myId: String by arguments
            val endTime: String by arguments

            handler.leaveMeetSession(meetSessionId, myId, endTime)
            true
        }
    }
}

val graphQlSchema: GraphQLSchema = SchemaGenerator().makeExecutableSchema(
    schemaDef(
        Paths.get("core.graphql")
    ), runtimeWiring
)