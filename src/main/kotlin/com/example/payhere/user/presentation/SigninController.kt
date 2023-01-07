package com.example.payhere.user.presentation

import com.example.payhere.user.application.SigninService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Email

@RestController
class SigninController(
    private val signinService: SigninService
) {
    @PostMapping("/user/signin")
    fun signin(@RequestBody @Valid dto: SigninRequestDto): ResponseEntity<SigninResponseDto> =
        ResponseEntity.ok().body(SigninResponseDto(signinService.command(dto.toCommand()).token))

    data class SigninRequestDto(
        @field:Email val username: String,
        var password: String
    ) {
        fun toCommand(): SigninService.SigninCommand = SigninService.SigninCommand(
            username = this.username,
            password = this.password
        )
    }

    data class SigninResponseDto(
        val token: String
    )
}