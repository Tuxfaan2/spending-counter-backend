package com.example.repo.spendingEntries

import com.example.repo.suspendTransaction

class PostgresSpendingEntryRepository : SpendingEntryRepository {
    override suspend fun getAllEntries(): List<SpendingEntry> = suspendTransaction {

    }

    override suspend fun getEntryById(id: Int): SpendingEntry? {
        TODO("Not yet implemented")
    }

    override suspend fun addEntry(spendingEntry: SpendingEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun removeEntry(name: String): Boolean {
        TODO("Not yet implemented")
    }
}