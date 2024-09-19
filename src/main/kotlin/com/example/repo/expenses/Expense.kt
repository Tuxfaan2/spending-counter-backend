package com.example.repo.expenses

import kotlinx.serialization.Serializable

@Serializable
data class Expense(
    val id: Int,
    val userId: Int,
    val description: String,
    val amount: Double
)