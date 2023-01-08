package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.ledger.domain.exception.IsNotDeletedException
import com.example.payhere.user.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class RestoreLedgerServiceDescribeSpec : DescribeSpec({
    val mockLedgerPersistencePort: LedgerPersistencePort = mockk()
    val restoreLedgerService = RestoreLedgerService(mockLedgerPersistencePort)

    describe("restoreLedger") {
        context("존재하지 않는 ledgerId를 가진 Command가 주어지면") {
            every { mockLedgerPersistencePort.findLedgerById(notExistsLedgerIdCommand.ledgerId) } answers { null }
            it("LedgerIdNotFoundException 발생") {
                shouldThrow<LedgerIdNotFoundException> {
                    restoreLedgerService.command(notExistsLedgerIdCommand)
                }
            }
        }

        context("존재하는 ledgerId를 가진 Command가 주어지면") {
            val expectedLedger = Ledger(price = 10000, memo = "memo", user = User("username", "password"))
            every { mockLedgerPersistencePort.findLedgerById(existsLedgerIdCommand.ledgerId) } answers { expectedLedger }

            context("삭제된 상태의 가계부가 아닌 경우") {
                it("IsNotDeletedException 발생") {
                    shouldThrow<IsNotDeletedException> {
                        restoreLedgerService.command(existsLedgerIdCommand)
                    }
                }
            }
        }
    }
}) {
    companion object {
        private val existsLedgerIdCommand = RestoreLedgerService.RestoreLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31"
        )

        private val notExistsLedgerIdCommand = RestoreLedgerService.RestoreLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31"
        )
    }
}