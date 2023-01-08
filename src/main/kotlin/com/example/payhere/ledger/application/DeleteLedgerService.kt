package com.example.payhere.ledger.application

import com.example.payhere.ledger.application.exception.LedgerIdNotFoundException
import com.example.payhere.ledger.domain.Ledger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class DeleteLedgerService(
    private val ledgerPersistencePort: LedgerPersistencePort
) {
    @Transactional
    fun command(command: DeleteLedgerCommand): DeleteLedgerInfo {
        val savedLedger = ledgerPersistencePort.findLedgerById(command.ledgerId)
        savedLedger ?: throw LedgerIdNotFoundException(command.ledgerId)
        savedLedger.delete()

        return DeleteLedgerInfo.from(savedLedger)
    }

    data class DeleteLedgerCommand(
        val ledgerId: String,
        val price: Long,
        val memo: String,
    )

    data class DeleteLedgerInfo(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(ledger: Ledger): DeleteLedgerInfo =
                DeleteLedgerInfo(
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