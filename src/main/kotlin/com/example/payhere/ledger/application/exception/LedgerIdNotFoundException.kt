package com.example.payhere.ledger.application.exception

import com.example.payhere.common.exception.BusinessException

class LedgerIdNotFoundException(ledgerId: String) : BusinessException(ledgerId) {
    override val message = "ledgerId가 존재하지 않습니다. : $ledgerId"
}