package com.example.repo.users

import com.example.repo.UserDAO
import com.example.repo.UserTable
import com.example.repo.daoToModel
import com.example.repo.suspendTransaction
import io.ktor.server.plugins.*

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
            userName = user.name
            userPassword = user.description
        }
    }

    override suspend fun removeUser(id: Int): Boolean = suspendTransaction {
        val tempUser = UserDAO.Companion
            .findById(id) ?: return@suspendTransaction false
        tempUser.delete()
        true
    }
}