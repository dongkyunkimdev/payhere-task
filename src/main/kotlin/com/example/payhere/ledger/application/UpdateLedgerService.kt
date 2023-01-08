package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import com.example.payhere.ledger.domain.Ledger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UpdateLedgerService(
    private val ledgerPersistencePort: LedgerPersistencePort
) {
    @Transactional
    fun command(command: UpdateLedgerCommand): UpdateLedgerInfo {
        val savedLedger = ledgerPersistencePort.findLedgerById(command.ledgerId)
        savedLedger ?: throw LedgerIdNotFoundException(command.ledgerId)
        savedLedger.update(command)

        return UpdateLedgerInfo.from(savedLedger)
    }

    data class UpdateLedgerCommand(
        val ledgerId: String,
        val price: Long,
        val memo: String,
    )

    data class UpdateLedgerInfo(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(ledger: Ledger): UpdateLedgerInfo =
                UpdateLedgerInfo(
                    username = ledger.user.username,
                    ledgerId = ledger.getId(),
                    price = ledger.price,
                    memo = ledger.memo,
                    createdAt = ledger.createdAt,
                    updatedAt = ledger.updatedAt
                )
        }
    }
}