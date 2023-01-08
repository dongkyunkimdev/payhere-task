package com.example.payhere.ledger.infrastructure.persistence.jpa

import com.example.payhere.ledger.domain.Ledger
import org.springframework.data.jpa.repository.JpaRepository

interface LedgerRepository : JpaRepository<Ledger, String> {
    fun findLedgerById(id: String): Ledger?
}