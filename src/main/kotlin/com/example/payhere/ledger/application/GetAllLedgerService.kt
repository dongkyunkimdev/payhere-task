package com.example.payhere.ledger.application

import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.user.application.UserPersistencePort
import com.example.payhere.user.application.exception.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllLedgerService(
    private val ledgerPersistencePort: LedgerPersistencePort,
    private val userPersistencePort: UserPersistencePort
) {
    @Transactional(readOnly = true)
    fun command(command: GetAllLedgerCommand): GetAllLedgerInfo {
        val savedUser = userPersistencePort.findUserByUsername(command.username)
        savedUser ?: throw UsernameNotFoundException(command.username)
        val savedLedgers = ledgerPersistencePort.findAllLedgerByUserId(savedUser.getId())

        return GetAllLedgerInfo.from(savedLedgers)
    }

    data class GetAllLedgerCommand(
        val username: String
    )

    data class GetAllLedgerInfo(
        val ledgers: List<GetLedgerService.GetLedgerInfo>
    ) {
        companion object {
            fun from(ledgers: List<Ledger>): GetAllLedgerInfo =
                GetAllLedgerInfo(
                    ledgers.map { GetLedgerService.GetLedgerInfo.from(it) }
                )
        }
    }
}