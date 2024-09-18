package com.example.repo.users

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun addUser(user: User)
    suspend fun removeUser(name: String): Boolean
}