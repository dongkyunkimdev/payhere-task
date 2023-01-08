package com.example.payhere.ledger.application

import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.user.application.UserPersistencePort
import com.example.payhere.user.application.exception.UsernameNotFoundException
import com.example.payhere.user.domain.User
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class WriteLedgerServiceDescribeSpec : DescribeSpec({
    val mockLedgerPersistencePort: LedgerPersistencePort = mockk()
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val writeLedgerService = WriteLedgerService(mockLedgerPersistencePort, mockUserPersistencePort)

    describe("writeLedger") {
        context("존재하지 않는 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findUserByUsername(notExistsUsernameCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    writeLedgerService.command(notExistsUsernameCommand)
                }
            }
        }

        context("존재하는 username을 가진 Command가 주어지면") {
            val expectedUser = User(existsUsernameCommand.username, "password")
            val expectedLedger = Ledger(existsUsernameCommand.price, existsUsernameCommand.memo, expectedUser)
            every { mockUserPersistencePort.findUserByUsername(existsUsernameCommand.username) } answers { expectedUser }
            every { mockLedgerPersistencePort.saveLedger(any()) } answers { expectedLedger }
            it("가계부 작성에 성공하고 올바른 값을 가진 WriteLedgerInfo 응답") {
                val info = writeLedgerService.command(existsUsernameCommand)

                assertSoftly {
                    info.price shouldBe existsUsernameCommand.price
                    info.memo shouldBe existsUsernameCommand.memo
                }
            }
        }
    }
}) {
    companion object {
        private val existsUsernameCommand = WriteLedgerService.WriteLedgerCommand(
            username = "username@email.com",
            price = 10000,
            memo = "memo"
        )

        private val notExistsUsernameCommand = WriteLedgerService.WriteLedgerCommand(
            username = "username@email.com",
            price = 10000,
            memo = "memo"
        )
    }
}