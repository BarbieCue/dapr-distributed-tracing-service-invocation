package org.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val servicePort = 8080
const val sidecarPort = 3500

fun main() {
    embeddedServer(Netty, port = servicePort, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {

        get("/") {
            call.respondText(
                HttpClient(CIO) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                }.get("http://localhost:${sidecarPort}/") {
                    header("dapr-app-id", "b-service")
                }.bodyAsText()
            )
        }
    }
}