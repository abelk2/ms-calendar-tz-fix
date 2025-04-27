package eu.abelk.tzfix

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val calendarContentType = ContentType.parse("text/calendar")
val calendarUrl = PropertiesLoader.loadProperties()["calendarUrl"] as String

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CallLogging)
        routing {
            get("/calendar.ics") {
                HttpClient(CIO).use { client ->
                    val response: HttpResponse = client.get(calendarUrl) {
                        accept(calendarContentType)
                    }
                    val body = response.bodyAsText()
                    call.respondText(SimpleTzidFixer.fixTzIds(body), calendarContentType)
                }
            }
        }
    }.start(wait = true)
}
