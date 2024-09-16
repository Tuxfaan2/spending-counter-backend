package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://poodle-firefly-2694.j77.aws-eu-central-1.cockroachlabs.cloud:26257/defaultdb",
        user = "postgres",
        password = "gEd9QPBhPoBkihA5_RmlJA",
    )
}
