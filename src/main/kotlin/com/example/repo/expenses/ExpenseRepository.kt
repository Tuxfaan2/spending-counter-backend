package com.example.repo.expenses

interface ExpenseRepository {
    suspend fun getAllExpenses(): List<Expense>
    suspend fun getExpenseById(id: Int): Expense?
    suspend fun addExpense(expense: Expense)
    suspend fun removeExpense(id: Int): Boolean
}