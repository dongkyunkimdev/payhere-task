package com.example.payhere.ledger.presentation

import com.example.payhere.ledger.application.GetAllLedgerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class GetAllLedgerController(
    private val getAllLedgerService: GetAllLedgerService
) {
    @GetMapping("/ledger/all")
    fun getAllLedger(@RequestHeader(name = "username", required = true) username: String)
            : ResponseEntity<GetAllLedgerResponseDto> {
        val info = getAllLedgerService.command(GetAllLedgerService.GetAllLedgerCommand(username))

        return ResponseEntity.ok().body(GetAllLedgerResponseDto.from(info))
    }

    data class GetAllLedgerResponseDto(
        val ledgers: List<GetLedgerController.GetLedgerResponseDto>
    ) {
        companion object {
            fun from(info: GetAllLedgerService.GetAllLedgerInfo): GetAllLedgerResponseDto =
                GetAllLedgerResponseDto(
                    info.ledgers.map { GetLedgerController.GetLedgerResponseDto.from(it) }
                )
        }
    }
}