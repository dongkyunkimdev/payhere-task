package com.example.payhere.user.application

import com.example.payhere.user.application.exception.IncorrectPasswordException
import com.example.payhere.user.application.exception.UsernameNotFoundException
import com.example.payhere.user.domain.User
import com.example.payhere.user.infrastructure.token.JwtProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SigninServiceDescribeSpec : DescribeSpec({
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val jwtProvider: JwtProvider = JwtProvider("secret", 300000)
    val mockPasswordEncoder: BCryptPasswordEncoder = mockk()
    val signinService = SigninService(mockUserPersistencePort, mockPasswordEncoder, jwtProvider)

    describe("signin") {
        context("존재하지 않는 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findUserByUsername(notExistsUsernameCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    signinService.command(notExistsUsernameCommand)
                }
            }
        }

        context("존재하는 username과") {
            every { mockUserPersistencePort.findUserByUsername(existsUsernameCommand.username) } answers {
                User(existsUsernameCommand.username, existsUsernameCommand.password)
            }
            context("일치하지 않는 password를 가진 Command가 주어지면") {
                every { mockPasswordEncoder.matches(any(), any()) } answers { false }
                it("IncorrectPasswordException 발생") {
                    shouldThrow<IncorrectPasswordException> {
                        signinService.command(existsUsernameCommand)
                    }
                }
            }

            context("일치하는 password를 가진 Command가 주어지면") {
                every { mockPasswordEncoder.matches(any(), any()) } answers { true }
                it("로그인에 성공하고 SigninInfo 응답") {
                    signinService.command(existsUsernameCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val existsUsernameCommand = SigninService.SigninCommand(
            username = "username@email.com",
            password = "password"
        )

        private val notExistsUsernameCommand = SigninService.SigninCommand(
            username = "username@email.com",
            password = "password"
        )
    }
}