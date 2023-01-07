package com.example.payhere.common.exception

open class BusinessException(override val message: String) : RuntimeException(message)