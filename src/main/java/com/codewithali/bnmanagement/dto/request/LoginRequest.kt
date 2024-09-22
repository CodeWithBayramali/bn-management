package com.codewithali.bnmanagement.dto.request

data class LoginRequest(
    val email: String,
    val hashedPassword: String
)
