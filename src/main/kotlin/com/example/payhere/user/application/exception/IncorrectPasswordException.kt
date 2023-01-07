package com.example.payhere.user.application.exception

import com.example.payhere.common.exception.BusinessException

class IncorrectPasswordException(username: String) : BusinessException(username) {
    override val message = "password가 일치하지 않습니다. : $username"
}