package com.example.payhere.user.application

import com.example.payhere.user.application.exception.DuplicateUsernameException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignupServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val passwordEncoder = BCryptPasswordEncoder()
    val signupService = SignupService(mockUserPersistencePort, passwordEncoder)

    describe("signup") {
        context("이미 회원가입된 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.existsUserByUsername(duplicatedUsernameCommand.username) } answers { true }
            it("DuplicateUsernameException 발생") {
                shouldThrow<DuplicateUsernameException> {
                    signupService.command(duplicatedUsernameCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val duplicatedUsernameCommand = SignupService.SignupCommand(
            username = "username@email.com",
            password = "password",
            name = "name"
        )
    }
}