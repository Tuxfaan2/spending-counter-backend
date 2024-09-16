package com.example.repo

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class PostgresUserRepository : UserRepository {
    private val users = mutableListOf<User>();

    override suspend fun getAllUsers(): List<User> = suspendTransaction {
        UserDAO.all().map(::daoToModel)
    }

    override suspend fun getUserById(name: String): User = suspendTransaction {
        UserDAO
            .find { (UserTable.name eq name) }
            .map(::daoToModel)[0]
    }
    override suspend fun addUser(user: User): Unit = suspendTransaction {
        UserDAO.new {
            name = user.name
            description = user.description
        }
    }

    override suspend fun removeUser(name: String): Boolean = suspendTransaction {
        val rowsDeleted =  UserTable.deleteWhere {
            UserTable.name eq name
        }
        rowsDeleted == 1
    }
}