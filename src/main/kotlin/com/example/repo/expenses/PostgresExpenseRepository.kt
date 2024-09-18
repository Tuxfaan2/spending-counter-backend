package com.example.repo.expenses

import com.example.repo.ExpenseDAO
import com.example.repo.ExpenseTable
import com.example.repo.daoToModel
import com.example.repo.suspendTransaction
import io.ktor.server.plugins.NotFoundException
import org.jetbrains.exposed.sql.deleteWhere

class PostgresExpenseRepository : ExpenseRepository {
    override suspend fun getAllExpenses(): List<Expense> = suspendTransaction {
        ExpenseDAO.Companion.all().map(::daoToModel)
    }

    override suspend fun getExpenseById(id: Int): Expense? = suspendTransaction {
        ExpenseDAO.Companion
            .find { (ExpenseTable.id eq id) }
            .map(::daoToModel).firstOrNull() ?: throw NotFoundException()
    }

    override suspend fun addExpense(expense: Expense): Unit = suspendTransaction {
        ExpenseDAO.Companion.new {
            expenseUserId = expense.userId
            expenseDescription = expense.description
            expenseAmount = expense.amount
        }
    }

    override suspend fun removeExpense(expenseId: Int): Boolean = suspendTransaction {
        val rowsDeleted = ExpenseTable.deleteWhere {
            ExpenseTable.id == expenseId
        }
        rowsDeleted == 1
    }
}