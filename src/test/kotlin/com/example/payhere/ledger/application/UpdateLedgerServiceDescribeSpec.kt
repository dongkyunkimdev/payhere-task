package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.user.domain.User
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
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

        context("존재하는 ledgerId를 가진 Command가 주어지면") {
            val expectedLedger = Ledger(price = 10000, memo = "memo", user = User("username", "password"))
            every { mockLedgerPersistencePort.findLedgerById(existsLedgerIdCommand.ledgerId) } answers { expectedLedger }
            it("가계부 수정에 성공하고 command와 일치하는 값을 가진 UpdateLedgerInfo 응답") {
                val info = updateLedgerService.command(existsLedgerIdCommand)

                assertSoftly {
                    info.price shouldBe existsLedgerIdCommand.price
                    info.memo shouldBe existsLedgerIdCommand.memo
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