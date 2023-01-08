package com.example.payhere.ledger.presentation

import com.example.payhere.ledger.application.UpdateLedgerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
class UpdateLedgerController(
    private val updateLedgerService: UpdateLedgerService
) {
    @PatchMapping("/ledger/update")
    fun updateLedger(@RequestBody @Valid dto: UpdateLedgerRequestDto): ResponseEntity<UpdateLedgerResponseDto> {
        val info = updateLedgerService.command(dto.toCommand())

        return ResponseEntity.ok().body(UpdateLedgerResponseDto.from(info))
    }

    data class UpdateLedgerRequestDto(
        val ledgerId: String,
        val price: Long,
        val memo: String,
    ) {
        fun toCommand(): UpdateLedgerService.UpdateLedgerCommand = UpdateLedgerService.UpdateLedgerCommand(
            ledgerId = ledgerId,
            price = price,
            memo = memo
        )
    }

    data class UpdateLedgerResponseDto(
        val username: String,
        val ledgerId: String,
        val price: Long,
        val memo: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(info: UpdateLedgerService.UpdateLedgerInfo): UpdateLedgerResponseDto =
                UpdateLedgerResponseDto(
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