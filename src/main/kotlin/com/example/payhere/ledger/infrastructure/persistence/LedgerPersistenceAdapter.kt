package com.example.payhere.ledger.infrastructure.persistence

import com.example.payhere.ledger.application.LedgerPersistencePort
import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.ledger.infrastructure.persistence.jpa.LedgerRepository
import org.springframework.stereotype.Component

@Component
class LedgerPersistenceAdapter(
    private val ledgerRepository: LedgerRepository
) : LedgerPersistencePort {
    override fun saveLedger(ledger: Ledger): Ledger = ledgerRepository.save(ledger)
    override fun findLedgerById(id: String): Ledger? = ledgerRepository.findLedgerById(id)
}