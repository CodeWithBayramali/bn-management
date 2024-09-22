package com.codewithali.bnmanagement.dto.request

data class CreateUserRequest(
    val name: String,
    val email: String,
    val hashedPassword: String
)
