package com.example.payhere.user.application.exception

import com.example.payhere.common.exception.BusinessException

class DuplicateUsernameException(username: String) : BusinessException(username) {
    override val message = "username이 이미 존재합니다. : $username"
}