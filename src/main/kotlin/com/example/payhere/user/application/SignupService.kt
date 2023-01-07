package com.example.payhere.user.application

import com.example.payhere.user.application.exception.DuplicateUsernameException
import com.example.payhere.user.domain.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupService(
    private val userPersistencePort: UserPersistencePort,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Transactional
    fun command(command: SignupCommand): SignupInfo {
        if (userPersistencePort.existsUserByUsername(command.username)) {
            throw DuplicateUsernameException(command.username)
        }
        command.password = passwordEncoder.encode(command.password)
        val savedUser = userPersistencePort.saveUser(command.toEntity())

        return SignupInfo.from(savedUser)
    }

    data class SignupCommand(
        val username: String,
        var password: String
    ) {
        fun toEntity(): User = User(
            username = this.username,
            password = this.password
        )
    }

    data class SignupInfo(
        val id: String,
        val username: String
    ) {
        companion object {
            fun from(user: User) = SignupInfo(
                id = user.getId(),
                username = user.username
            )
        }
    }
}