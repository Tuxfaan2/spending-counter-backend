package com.example.repo

import com.example.repo.expenses.Expense
import com.example.repo.users.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable : IntIdTable("users") {
    val userName = varchar("user_name", 50)
    val userPassword = varchar("user_password", 255)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)

    var userId by UserTable.id
    var userName by UserTable.userName
    var userPassword by UserTable.userPassword
}

fun daoToModel(dao: UserDAO) = User(
    dao.userId.value,
    dao.userName,
    dao.userPassword,
)

object ExpenseTable : IntIdTable("expenses") {
    var expenseUserId = integer("expense_user_id")
    var expenseDescription = varchar("expense_description", 255)
    var expenseAmount = double("expense_amount")
}

class ExpenseDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExpenseDAO>(ExpenseTable)

    var expenseId by ExpenseTable.id
    var expenseUserId by ExpenseTable.expenseUserId
    var expenseDescription by ExpenseTable.expenseDescription
    var expenseAmount by ExpenseTable.expenseAmount
}

fun daoToModel(dao: ExpenseDAO) = Expense(
    dao.expenseId.value,
    dao.expenseUserId,
    dao.expenseDescription,
    dao.expenseAmount,
)

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)
