package com.example.plugins

import com.example.repo.expenses.Expense
import com.example.repo.expenses.PostgresExpenseRepository
import com.example.repo.users.PostgresUserRepository
import com.example.repo.users.User
import io.ktor.http.*
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        route("/users") {
            val userRepo = PostgresUserRepository()
            get {
                val users = userRepo.getAllI()
                call.respond(users)
            }

            get("/byId/{id}") {
                val id = call.parameters["id"]?.toInt()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val user = userRepo.getById(id)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(user)
            }

            post {
                try {
                    val user = call.receive<User>()
                    userRepo.add(user)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                if (userRepo.remove(id.toInt())) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
        route("/expenses") {
            val expenseRepo = PostgresExpenseRepository()
            get {
                val expenses = expenseRepo.getAllI()
                call.respond(expenses)
            }
            get("/byId/{id}") {
                val id = call.parameters["id"]?.toInt()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val expense = expenseRepo.getById(id)
                if (expense == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(expense)
            }

            post {
                try {
                    val expense = call.receive<Expense>()
                    expenseRepo.add(expense)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toInt()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                if (expenseRepo.remove(id.toInt())) {
                    call.respond(HttpStatusCode.NoContent)
                    return@delete
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}
