package io.github.meetlog.server.graphql

import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import io.github.meetlog.server.exception.GraphQlClientException

val formatErrorGraphQlError: GraphQLError.() -> Map<String, Any> = {
    val clientMessage = if (this is ExceptionWhileDataFetching) {

        val formattedMessage = if (exception is GraphQlClientException) {
            exception.message
        } else {
            "Internal server error"
        }

        formattedMessage
    } else {
        message
    }

    val result = toSpecification()
    result["message"] = clientMessage
    result
}