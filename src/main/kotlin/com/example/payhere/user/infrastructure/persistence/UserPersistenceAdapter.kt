package com.example.payhere.user.infrastructure.persistence

import com.example.payhere.user.application.UserPersistencePort
import com.example.payhere.user.domain.User
import com.example.payhere.user.infrastructure.persistence.jpa.UserRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : UserPersistencePort {
    override fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    override fun existsUserByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }
}