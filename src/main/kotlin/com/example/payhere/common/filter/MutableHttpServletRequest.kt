package com.example.payhere.common.filter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class MutableHttpServletRequest(
    request: HttpServletRequest
) : HttpServletRequestWrapper(request) {
    private val customHeaders: HashMap<String, String> = hashMapOf()

    fun putHeader(name: String, value: String) {
        customHeaders[name] = value
    }
}