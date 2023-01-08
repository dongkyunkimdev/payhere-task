package com.example.payhere.ledger.presentation

import com.example.payhere.ledger.application.GetLedgerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class GetLedgerController(
    private val getLedgerService: GetLedgerService
) {
    @GetMapping("/ledger/{ledgerId}")
    fun getLedger(@PathVariable ledgerId: String): ResponseEntity<GetLedgerResponseDto> {
        val info = getLedgerService.command(GetLedgerService.GetLedgerCommand(ledgerId))

        return ResponseEntity.ok().body(GetLedgerResponseDto.from(info))
    }

    data class GetLedgerResponseDto(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: GetLedgerService.GetLedgerInfo): GetLedgerResponseDto =
                GetLedgerResponseDto(
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