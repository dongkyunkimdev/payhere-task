package com.example.payhere.ledger.presentation

import com.example.payhere.ledger.application.WriteLedgerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class WriteLedgerController(
    private val writeLedgerService: WriteLedgerService
) {
    @PostMapping("/ledger/write")
    fun writeLedger(
        @RequestBody @Valid dto: WriteLedgerRequestDto,
        @RequestHeader(name = "username", required = true) username: String
    ): ResponseEntity<WriteLedgerResponseDto> {
        val command = WriteLedgerService.WriteLedgerCommand(username = username, price = dto.price, memo = dto.memo)

        return ResponseEntity.ok().body(WriteLedgerResponseDto.from(writeLedgerService.command(command)))
    }

    data class WriteLedgerRequestDto(
        val price: Long,
        val memo: String
    )

    data class WriteLedgerResponseDto(
        val price: Long,
        val memo: String
    ) {
        companion object {
            fun from(info: WriteLedgerService.WriteLedgerInfo) = WriteLedgerResponseDto(
                price = info.price,
                memo = info.memo
            )
        }
    }
}

