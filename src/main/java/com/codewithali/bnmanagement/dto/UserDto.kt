package com.codewithali.bnmanagement.dto

import com.codewithali.bnmanagement.model.Role
import com.codewithali.bnmanagement.model.User

data class UserDto(
    val id: String?,
    val name: String,
    val email: String,
    val role: Role?,
) {
    companion object {
        @JvmStatic
        fun convertToUserDto(from: User): UserDto {
            return UserDto(
                from.id,
                from.name,
                from.email,
                from.roles?.firstOrNull()
            )
        }
    }
}
