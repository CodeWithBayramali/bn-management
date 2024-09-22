package com.codewithali.bnmanagement.dto.response

import org.springframework.http.HttpStatusCode
import java.util.Objects

data class SuccessReponse(
    val message: String,
    val success: Int
)
