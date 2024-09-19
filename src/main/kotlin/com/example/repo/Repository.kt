package com.example.repo

interface Repository<T> {
    suspend fun getAllItems(): List<T>
    suspend fun getItemById(id: Int): T?
    suspend fun addItem(item: T)
    suspend fun removeItem(id: Int): Boolean
}