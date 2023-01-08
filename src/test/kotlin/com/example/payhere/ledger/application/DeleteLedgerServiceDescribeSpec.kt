package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class DeleteLedgerServiceDescribeSpec : DescribeSpec({
    val mockLedgerPersistencePort: LedgerPersistencePort = mockk()
    val deleteLedgerService = DeleteLedgerService(mockLedgerPersistencePort)

    describe("deleteLedger") {
        context("존재하지 않는 ledgerId를 가진 Command가 주어지면") {
            every { mockLedgerPersistencePort.findLedgerById(notExistsLedgerIdCommand.ledgerId) } answers { null }
            it("LedgerIdNotFoundException 발생") {
                shouldThrow<LedgerIdNotFoundException> {
                    deleteLedgerService.command(notExistsLedgerIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val existsLedgerIdCommand = DeleteLedgerService.DeleteLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31"
        )

        private val notExistsLedgerIdCommand = DeleteLedgerService.DeleteLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31"
        )
    }
}