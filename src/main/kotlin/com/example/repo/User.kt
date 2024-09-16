package com.example.repo

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String, val description: String, val id: Int)
