package io.github.meetlog.server.graphql

import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring

class RuntimeWiringBuilder {
    private val builder = RuntimeWiring.newRuntimeWiring()

    fun type(typeName: String, operation: TypeRuntimeWiringBuilder.() -> Unit) {
        val wiring = TypeRuntimeWiringBuilder(typeName).apply(operation)

        builder.type(wiring.build())
    }

    internal fun build() = builder.build()
}

@Suppress("FunctionName")
fun RuntimeWiring(
    operation: RuntimeWiringBuilder.() -> Unit
): RuntimeWiring {
    val builder = RuntimeWiringBuilder()
    operation(builder)
    return builder.build()
}

class TypeRuntimeWiringBuilder(name: String) {
    private val builder = TypeRuntimeWiring.newTypeWiring(name)

    fun <T> dataFetcher(fieldName: String, fetcher: DataFetchingEnvironment.() -> T) {
        builder.dataFetcher(fieldName, fetcher)
    }

    internal fun build(): TypeRuntimeWiring = builder.build()
}