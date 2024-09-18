package com.example.repo.expenses

import java.math.BigDecimal

data class Expense(val id: Int, val userId: Int, val description: String, val amount: BigDecimal)