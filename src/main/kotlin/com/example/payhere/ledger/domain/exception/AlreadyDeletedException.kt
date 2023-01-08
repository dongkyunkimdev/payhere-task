package com.example.payhere.ledger.domain.exception

import com.example.payhere.common.exception.BusinessException

class AlreadyDeletedException(ledgerId: String) : BusinessException(ledgerId) {
    override val message = "이미 삭제된 상태의 가계부입니다. : $ledgerId"
}