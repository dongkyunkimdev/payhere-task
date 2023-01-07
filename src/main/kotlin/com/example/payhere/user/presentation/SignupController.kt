package com.example.payhere.user.presentation

import com.example.payhere.user.application.SignupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Email

@RestController
class SignupController(
    private val signupService: SignupService
) {
    @PostMapping("/user/signup")
    fun signup(@RequestBody @Valid dto: SignupRequestDto): ResponseEntity<SignupResponseDto> =
        ResponseEntity(SignupResponseDto.from(signupService.command(dto.toCommand())), HttpStatus.CREATED)

    data class SignupRequestDto(
        @field:Email val username: String,
        var password: String
    ) {
        fun toCommand(): SignupService.SignupCommand = SignupService.SignupCommand(
            username = this.username,
            password = this.password
        )
    }

    data class SignupResponseDto(
        val id: String,
        val username: String
    ) {
        companion object {
            fun from(info: SignupService.SignupInfo) = SignupResponseDto(
                id = info.id,
                username = info.username
            )
        }
    }
}