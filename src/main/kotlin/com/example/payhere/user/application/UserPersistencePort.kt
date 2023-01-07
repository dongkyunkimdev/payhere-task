package com.example.payhere.user.application

import com.example.payhere.user.domain.User

interface UserPersistencePort {
    fun saveUser(user: User): User
    fun existsUserByUsername(username: String): Boolean
}