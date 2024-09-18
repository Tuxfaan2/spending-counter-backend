package com.example.repo.users

import com.example.repo.UserDAO
import com.example.repo.UserTable
import com.example.repo.daoToModel
import com.example.repo.suspendTransaction
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class PostgresUserRepository : UserRepository {

    override suspend fun getAllUsers(): List<User> = suspendTransaction {
        UserDAO.Companion.all().map(::daoToModel)
    }

    override suspend fun getUserById(id: Int): User = suspendTransaction {
        UserDAO.Companion
            .find { (UserTable.id eq id) }
            .map(::daoToModel).firstOrNull() ?: throw NotFoundException()
    }
    override suspend fun addUser(user: User): Unit = suspendTransaction {
        UserDAO.Companion.new {
            user_name = user.name
            user_password = user.description
        }
    }

    override suspend fun removeUser(name: String): Boolean = suspendTransaction {
        val rowsDeleted = UserTable.deleteWhere {
            UserTable.user_name eq name
        }
        rowsDeleted == 1
    }
}