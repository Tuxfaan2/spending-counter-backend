package com.example

import com.example.plugins.*
import com.example.repo.DbFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun temp() {

}

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureRouting()
    DbFactory.init(environment)
}
