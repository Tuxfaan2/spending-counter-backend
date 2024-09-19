package com.example.repo

interface Repository<T> {
    suspend fun getAllI(): List<T>
    suspend fun getById(id: Int): T?
    suspend fun add(item: T)
    suspend fun remove(id: Int): Boolean
}