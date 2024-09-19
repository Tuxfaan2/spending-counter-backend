package com.example.repo.expenses

import com.example.repo.ExpenseDAO
import com.example.repo.ExpenseTable
import com.example.repo.Repository
import com.example.repo.daoToModel
import com.example.repo.suspendTransaction

class PostgresExpenseRepository : Repository<Expense> {
    override suspend fun getAll(): List<Expense> = suspendTransaction {
        ExpenseDAO.Companion.all().map(::daoToModel)
    }

    override suspend fun getById(id: Int): Expense? = suspendTransaction {
        ExpenseDAO.Companion
            .find { (ExpenseTable.id eq id) }
            .map(::daoToModel).firstOrNull()
    }

    override suspend fun add(expense: Expense): Unit = suspendTransaction {
        ExpenseDAO.Companion.new {
            expenseUserId = expense.userId
            expenseDescription = expense.description
            expenseAmount = expense.amount
        }
    }

    override suspend fun remove(id: Int): Boolean = suspendTransaction {
        val tempExpense = ExpenseDAO
            .findById(id) ?: return@suspendTransaction false
        tempExpense.delete()
        true
    }
}