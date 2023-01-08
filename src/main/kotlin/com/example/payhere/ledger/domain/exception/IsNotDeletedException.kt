package com.example.payhere.ledger.domain.exception

import com.example.payhere.common.exception.BusinessException

class IsNotDeletedException(ledgerId: String) : BusinessException(ledgerId) {
    override val message = "삭제된 상태의 가계부가 아닙니다. : $ledgerId"
}