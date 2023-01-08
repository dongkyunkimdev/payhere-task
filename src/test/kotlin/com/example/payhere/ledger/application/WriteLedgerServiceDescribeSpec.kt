package com.example.payhere.ledger.application

import com.example.payhere.user.application.UserPersistencePort
import com.example.payhere.user.application.exception.UsernameNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
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