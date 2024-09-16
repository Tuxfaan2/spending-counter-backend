package com.example

import com.example.plugins.*
import com.example.repo.PostgresUserRepository
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun temp() {

}

fun Application.module() {
    val repository = PostgresUserRepository()
    configureHTTP()
    configureSerialization(repository)
    configureDatabases()
    configureRouting()
}
