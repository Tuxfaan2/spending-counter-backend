package com.example.repo

import com.example.repo.users.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable : IntIdTable("users") {
    val user_name = varchar("name", 50)
    val user_password = varchar("description", 255)
}
class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)
    var user_name by UserTable.user_name
    var user_password by UserTable.user_password
    var user_id by UserTable.id
}

fun daoToModel(dao: UserDAO) = User(
    dao.user_name,
    dao.user_password,
    dao.user_id.value,
)

object ExpenseEntryTable : IntIdTable("") {

    val spending_description = varchar("description", 255);
    val spending_amount
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)
