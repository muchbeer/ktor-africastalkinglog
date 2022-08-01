package com.muchbeerat

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.muchbeerat.plugins.*

fun main() {
   // embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
    embeddedServer(Netty, port = 8085, host = "192.168.43.101") {
        configureRouting()
    }.start(wait = true)
}
