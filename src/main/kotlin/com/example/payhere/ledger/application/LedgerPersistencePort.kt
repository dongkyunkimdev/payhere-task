package com.example.payhere.ledger.application

import com.example.payhere.ledger.domain.Ledger

interface LedgerPersistencePort {
    fun saveLedger(ledger: Ledger): Ledger
    fun findLedgerById(id: String): Ledger?
}