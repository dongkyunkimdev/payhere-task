package com.example.payhere.common.filter

import com.example.payhere.common.exception.JwtValidateException
import com.example.payhere.common.util.JwtUtil
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!isPermitPath(request.servletPath)) {
            try {
                val token = jwtUtil.getToken(
                    request.getHeader("Authorization") ?: throw JwtValidateException("empty Authorization Header")
                )
                val claims = jwtUtil.resolveToken(token)
                val mutableRequest: MutableHttpServletRequest = MutableHttpServletRequest(request)
                mutableRequest.putHeader("username", claims.subject)

                filterChain.doFilter(mutableRequest, response)
            } catch (e: JwtValidateException) {
                response.status = 401
                return
            }
        } else {
            filterChain.doFilter(request, response)
        }
    }

    private fun isPermitPath(path: String): Boolean {
        return API_WHITELIST_GUEST.contains(path)
    }

    companion object {
        val API_WHITELIST_GUEST = listOf<String>("/user/signup", "/user/signin")
    }
}