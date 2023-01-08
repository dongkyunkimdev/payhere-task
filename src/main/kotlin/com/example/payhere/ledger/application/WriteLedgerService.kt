package com.example.payhere.ledger.application

import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.user.application.UserPersistencePort
import com.example.payhere.user.application.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WriteLedgerService(
    private val ledgerPersistencePort: LedgerPersistencePort,
    private val userPersistencePort: UserPersistencePort
) {
    @Transactional
    fun command(command: WriteLedgerCommand): WriteLedgerInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)
        val savedLedger = ledgerPersistencePort.saveLedger(Ledger(command.price, command.memo, savedUser))

        return WriteLedgerInfo(ledgerId = savedLedger.getId(), price = savedLedger.price, memo = savedLedger.memo)
    }

    data class WriteLedgerCommand(
        val username: String,
        val price: Long,
        val memo: String
    )

    data class WriteLedgerInfo(
        val ledgerId: String,
        val price: Long,
        val memo: String
    )
}