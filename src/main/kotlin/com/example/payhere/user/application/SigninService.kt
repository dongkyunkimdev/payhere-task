package com.example.payhere.user.application

import com.example.payhere.user.application.exception.IncorrectPasswordException
import com.example.payhere.user.application.exception.UsernameNotFoundException
import com.example.payhere.user.infrastructure.token.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SigninService(
    private val userPersistencePort: UserPersistencePort,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtProvider: JwtProvider
) {
    @Transactional
    fun command(command: SigninCommand): SigninInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)
        if (!savedUser.isCorrectPassword(passwordEncoder, command.password)) throw IncorrectPasswordException(command.username)
        val token = jwtProvider.generateToken(savedUser.username)

        return SigninInfo(token)
    }

    data class SigninCommand(
        val username: String,
        var password: String
    )

    data class SigninInfo(
        val token: String
    )
}