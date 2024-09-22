package com.codewithali.bnmanagement.model

import org.springframework.security.core.GrantedAuthority

enum class Role(private val value: String): GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    override fun getAuthority(): String {
        return name
    }

    fun getValue(): String {
        return value
    }

}