package com.muchbeerat.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import com.muchbeerat.model.ATMessage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.json.XML
import org.slf4j.event.Level

fun Application.configureRouting() {

    // Starting point for a Ktor app:



    log.info("AT application dependency started!")

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
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
            val response : ATMessage = handlesms()
                     call.respond(response)

         }
    }
}
  suspend fun handlesms() : ATMessage  {
      val httpClient = HttpClient(CIO)
      val gson = Gson()
      val PRETTY_PRINT_INDENT_FACTOR = 8

     val response : String = httpClient.submitForm(

          url = "https://api.africastalking.com/version1/messaging",
          formParameters = Parameters.build {
              append("username", "muchbeerx")
              append("to", "255757022731")
              append("message", "Start from the string")
              append("from", "CHARLES DAY")
          }, block = {
              headers {
                  append("apiKey", "eabefe5adbeb4cd13112ec092ad9a883a0f38f75ba49c20d44869ca6ca22474c")
                }
          }

      ).bodyAsText()

   val jsonObj = XML.toJSONObject(response)
     val jsonPrettyPrintString = jsonObj.toString(PRETTY_PRINT_INDENT_FACTOR)
      val responseObject =   gson.fromJson(jsonPrettyPrintString, ATMessage::class.java)

      return responseObject
 }