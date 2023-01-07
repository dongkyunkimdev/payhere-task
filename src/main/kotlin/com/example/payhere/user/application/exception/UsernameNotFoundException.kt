package com.example.payhere.user.application.exception

import com.example.payhere.common.exception.BusinessException

class UsernameNotFoundException(username: String) : BusinessException(username) {
    override val message = "username이 존재하지 않습니다. : $username"
}