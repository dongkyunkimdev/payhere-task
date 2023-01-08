package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import com.example.payhere.ledger.domain.Ledger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class GetLedgerService(
    private val ledgerPersistencePort: LedgerPersistencePort
) {
    @Transactional(readOnly = true)
    fun command(command: GetLedgerCommand): GetLedgerInfo {
        val savedLedger = ledgerPersistencePort.findLedgerById(command.ledgerId)
        savedLedger ?: throw LedgerIdNotFoundException(command.ledgerId)

        return GetLedgerInfo.from(savedLedger)
    }

    data class GetLedgerCommand(
        val ledgerId: String
    )

    data class GetLedgerInfo(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(ledger: Ledger): GetLedgerInfo =
                GetLedgerInfo(
                    username = ledger.user.getId(),
                    ledgerId = ledger.getId(),
                    price = ledger.price,
                    memo = ledger.memo,
                    createdAt = ledger.createdAt,
                    updatedAt = ledger.updatedAt
                )
        }
    }
}