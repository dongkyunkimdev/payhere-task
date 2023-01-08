package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.user.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class GetLedgerServiceDescribeSpec : DescribeSpec({
    val mockLedgerPersistencePort: LedgerPersistencePort = mockk()
    val getLedgerService = GetLedgerService(mockLedgerPersistencePort)

    describe("getLedger") {
        context("존재하지 않는 ledgerId를 가진 Command가 주어지면") {
            every { mockLedgerPersistencePort.findLedgerById(notExistsLedgerIdCommand.ledgerId) } answers { null }
            it("LedgerIdNotFoundException 발생") {
                shouldThrow<LedgerIdNotFoundException> {
                    getLedgerService.command(notExistsLedgerIdCommand)
                }
            }
        }

        context("존재하는 ledgerId를 가진 Command가 주어지면") {
            val expectedLedger = Ledger(price = 10000, memo = "memo", user = User("username", "password"))
            every { mockLedgerPersistencePort.findLedgerById(existsLedgerIdCommand.ledgerId) } answers { expectedLedger }
            it("가계부 상세 조회에 성공하고 GetLedgerInfo 응답") {
                val info = getLedgerService.command(existsLedgerIdCommand)
            }
        }
    }
}) {
    companion object {
        private val existsLedgerIdCommand = GetLedgerService.GetLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31"
        )

        private val notExistsLedgerIdCommand = GetLedgerService.GetLedgerCommand(
            ledgerId = "018590ae-b512-6049-3c0e-cc3ff8752f31"
        )
    }
}