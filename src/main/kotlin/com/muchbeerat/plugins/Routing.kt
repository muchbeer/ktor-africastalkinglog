package com.muchbeerat.plugins

import com.muchbeerat.ATMessageResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureRouting() {

    // Starting point for a Ktor app:

    log.info("AT application dependency started!")
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            call.request.path().startsWith("/africastalking")
        }

    }
    routing {
        get("/africastalking") {
            call.application.environment.log.info("Hello from /africastalking")
            call.respondText("Getting to check the gradle error")
        }

        get("/") {
            call.application.environment.log.info("Launch the sms application")
            call.respond(handlesms(httpClient))
         }
    }
}
  suspend fun handlesms(httpClient : HttpClient) : String  {

      val response: String = httpClient.submitForm(

          url = "https://api.africastalking.com/version1/messaging",
          formParameters = Parameters.build {
              append("username", "muchbeerx")
              append("to", "255757022731")
              append("message", "Making the best app from ktor")
              append("from", "CHARLES DAY")
          }, block = {
                HttpMethod.Post
              headers {
                  append("apiKey", "eabefe5adbeb4cd13112ec092ad9a883a0f38f75ba49c20d44869ca6ca22474c")
                  append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                  append(HttpHeaders.Accept, "application/json")}
          }

      ).body()
    return response
 }