package com.muchbeerat.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureRouting() {

    // Starting point for a Ktor app:

    log.info("AT application dependency started!")

    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            call.request.path().startsWith("/africastalking")
        }

        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            "Status: $status, HTTP method: $httpMethod, User agent: $userAgent"
        }
    }
    routing {
        get("/africastalking") {
            call.application.environment.log.info("Hello from /africastalking")
            call.respondText("Getting to check the gradle error")
        }
    }
    routing {
    }
}
