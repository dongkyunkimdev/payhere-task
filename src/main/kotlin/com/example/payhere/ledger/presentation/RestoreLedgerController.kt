package com.example.payhere.ledger.presentation

import com.example.payhere.ledger.application.RestoreLedgerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class RestoreLedgerController(
    private val restoreLedgerService: RestoreLedgerService
) {
    @PatchMapping("/ledger/restore/{ledgerId}")
    fun restoreLedger(@PathVariable ledgerId: String): ResponseEntity<RestoreLedgerResponseDto> {
        val info = restoreLedgerService.command(RestoreLedgerService.RestoreLedgerCommand(ledgerId))

        return ResponseEntity.ok().body(RestoreLedgerResponseDto.from(info))
    }

    data class RestoreLedgerResponseDto(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: RestoreLedgerService.RestoreLedgerInfo): RestoreLedgerResponseDto =
                RestoreLedgerResponseDto(
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