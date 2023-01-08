package com.example.payhere.ledger.application

import com.example.payhere.user.application.UserPersistencePort
import com.example.payhere.user.application.exception.UsernameNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class GetAllLedgerServiceDescribeSpec : DescribeSpec({
    val mockLedgerPersistencePort: LedgerPersistencePort = mockk()
    val mockUserPersistencePort: UserPersistencePort = mockk()
    val getAllLedgerService = GetAllLedgerService(mockLedgerPersistencePort, mockUserPersistencePort)

    describe("getAllLedger") {
        context("존재하지 않는 username을 가진 Command가 주어지면") {
            every { mockUserPersistencePort.findUserByUsername(notExistsUsernameCommand.username) } answers { null }
            it("UsernameNotFoundException 발생") {
                shouldThrow<UsernameNotFoundException> {
                    getAllLedgerService.command(notExistsUsernameCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val existsUsernameCommand = GetAllLedgerService.GetAllLedgerCommand(
            username = "username@email.com"
        )

        private val notExistsUsernameCommand = GetAllLedgerService.GetAllLedgerCommand(
            username = "username@email.com"
        )
    }
}