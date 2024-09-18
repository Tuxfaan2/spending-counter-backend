package com.example.repo.spendingEntries

interface SpendingEntryRepository {
    suspend fun getAllEntries(): List<SpendingEntry>
    suspend fun getEntryById(id: Int): SpendingEntry?
    suspend fun addEntry(spendingEntry: SpendingEntry)
    suspend fun removeEntry(name: String): Boolean
}