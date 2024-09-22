package com.codewithali.bnmanagement.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.userdetails.UserDetails

@Document
data class User @JvmOverloads constructor(
    val name: String,
    val email: String,
    val hashedPassword: String,
    @Id val id: String? = null,
    val roles: Set<Role>? = setOf(Role.USER)
): UserDetails {
    override fun getAuthorities() = this.roles

    override fun getPassword() = this.hashedPassword

    override fun getUsername() = this.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked()= true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled()= true
}
