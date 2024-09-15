package com.example.repo

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database


fun hikari(url: String, user: String, pass: String, pool: Int): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.postgresql.Driver"
    config.jdbcUrl = url
    config.maximumPoolSize = pool
    config.username = user
    config.password = pass
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    return HikariDataSource(config)
}

object DbFactory {
    fun init(environment: ApplicationEnvironment){
        val url = environment.config.property("db.url").getString()
        val username = environment.config.property("db.username").getString()
        val password = environment.config.property("db.password").getString()
        val poolSize = environment.config.property("db.poolSize").getString().toInt()

        Database.connect(hikari(url, username, password, poolSize))
    }
}