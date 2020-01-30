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

private val handler = GraphQlHandler()
val runtimeWiring = RuntimeWiring {
    type("Query") {
        dataFetcher("getSession") {
            val args = it.arguments

            val token: String by args
            val friendsId: String by args
            val meetSessionId: String by args

            TODO() //handler.getSession(token, friendsId, meetSessionId)
        }
    }
}

val graphQlSchema: GraphQLSchema = SchemaGenerator().makeExecutableSchema(
    schemaDef(
        Paths.get("")
    ), runtimeWiring
) // TODO set path