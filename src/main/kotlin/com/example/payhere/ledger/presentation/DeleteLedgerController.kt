package com.example.payhere.ledger.presentation

import com.example.payhere.ledger.application.DeleteLedgerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class DeleteLedgerController(
    private val deleteLedgerService: DeleteLedgerService
) {
    @DeleteMapping("/ledger/delete/{ledgerId}")
    fun deleteLedger(@PathVariable ledgerId: String): ResponseEntity<DeleteLedgerResponseDto> {
        val info = deleteLedgerService.command(DeleteLedgerService.DeleteLedgerCommand(ledgerId))

        return ResponseEntity.ok().body(DeleteLedgerResponseDto.from(info))
    }

    data class DeleteLedgerResponseDto(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: DeleteLedgerService.DeleteLedgerInfo): DeleteLedgerResponseDto =
                DeleteLedgerResponseDto(
                    username = info.username,
                    ledgerId = info.ledgerId,
                    price = info.price,
                    memo = info.memo,
                    createdAt = info.createdAt,
                    updatedAt = info.updatedAt
                )
        }
    }
}