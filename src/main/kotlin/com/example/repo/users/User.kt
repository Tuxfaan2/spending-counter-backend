package com.example.repo.users

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String, val description: String)
