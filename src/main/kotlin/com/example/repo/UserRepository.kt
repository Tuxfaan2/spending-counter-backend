package com.example.repo

import com.example.repo.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(name: String): User?
    suspend fun addUser(user: User)
    suspend fun removeUser(name: String): Boolean
}