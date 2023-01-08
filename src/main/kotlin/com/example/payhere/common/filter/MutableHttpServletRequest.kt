package com.example.payhere.common.filter

import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper


class MutableHttpServletRequest(
    request: HttpServletRequest
) : HttpServletRequestWrapper(request) {
    private val customHeaders: HashMap<String, String> = hashMapOf()

    fun putHeader(name: String, value: String) {
        customHeaders[name] = value
    }

    override fun getHeader(name: String): String {
        val headerValue = customHeaders[name]

        return headerValue ?: (request as HttpServletRequest).getHeader(name)
    }

    override fun getHeaderNames(): Enumeration<String> {
        val set: MutableSet<String> = HashSet(customHeaders.keys)

        val e = (request as HttpServletRequest).headerNames
        while (e.hasMoreElements()) {
            val n = e.nextElement()
            set.add(n)
        }
        return Collections.enumeration(set)
    }

    override fun getHeaders(name: String): Enumeration<String> {
        val set = mutableSetOf<String>()
        customHeaders[name]?.let { set.add(it) }
        val e = (request as HttpServletRequest).getHeaders(name)
        while (e.hasMoreElements()) {
            val n = e.nextElement()
            set.add(n)
        }
        customHeaders[name]?.let { set.add(it) }

        return Collections.enumeration(set)
    }
}