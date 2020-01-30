package io.github.meetlog.server.graphql

import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring

class RuntimeWiringBuilder {
    private val builder = RuntimeWiring.newRuntimeWiring()

    fun type(typeName: String, operation: TypeRuntimeWiring.Builder.() -> Unit) {
        val wiring = TypeRuntimeWiring.newTypeWiring(typeName)
        operation(wiring)
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