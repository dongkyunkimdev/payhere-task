package com.example.payhere.user.infrastructure.persistence.jpa

import com.example.payhere.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun existsByUsername(username: String): Boolean
}