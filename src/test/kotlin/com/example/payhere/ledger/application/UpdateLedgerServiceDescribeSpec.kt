package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class UpdateLedgerServiceDescribeSpec : DescribeSpec({
    val mockLedgerPersistencePort: LedgerPersistencePort = mockk()
    val updateLedgerService = UpdateLedgerService(mockLedgerPersistencePort)

    describe("updateLedger") {
        context("존재하지 않는 ledgerId를 가진 Command가 주어지면") {
            every { mockLedgerPersistencePort.findLedgerById(notExistsLedgerIdCommand.ledgerId) } answers { null }
            it("LedgerIdNotFoundException 발생") {
                shouldThrow<LedgerIdNotFoundException> {
                    updateLedgerService.command(notExistsLedgerIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val existsLedgerIdCommand = UpdateLedgerService.UpdateLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31",
            price = 123456,
            memo = "updatedMemo"
        )

        private val notExistsLedgerIdCommand = UpdateLedgerService.UpdateLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31",
            price = 123456,
            memo = "updatedMemo"
        )
    }
}