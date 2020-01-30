package io.github.meetlog.server.graphql

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext

suspend fun PipelineContext<Unit, ApplicationCall>.authenticate() {
    val token = call.request.headers["Authorization"]?.substring("Bearer ".length)

    //
}