package io.github.meetlog.server.exception

abstract class GraphQlException: Exception {
    constructor(message: String? = null): super(message)

    constructor(message: String? = null, cause: Throwable): super(message, cause)
}

class GraphQlClientException: GraphQlException {
    constructor(message: String? = null): super(message)

    constructor(message: String? = null, cause: Throwable): super(message, cause)
}