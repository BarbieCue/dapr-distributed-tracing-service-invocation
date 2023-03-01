package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val servicePort = 8083
const val httpTraceHeader = "traceparent"

fun main() {
    embeddedServer(Netty, port = servicePort, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    routing {

        get("/") {
            call.respondText("Hi from D. I am the end of the trace ${call.request.header(httpTraceHeader)}")
        }
    }
}