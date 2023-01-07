package com.example.payhere.user.presentation

import com.example.payhere.user.application.SignupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignupController(
    private val signupService: SignupService
) {
    @PostMapping("/user/signup")
    fun signup(@RequestBody dto: SignupRequestDto): ResponseEntity<SignupResponseDto> =
        ResponseEntity(SignupResponseDto.from(signupService.command(dto.toCommand())), HttpStatus.CREATED)

    data class SignupRequestDto(
        val username: String,
        var password: String,
        val name: String
    ) {
        fun toCommand(): SignupService.SignupCommand = SignupService.SignupCommand(
            username = this.username,
            password = this.password,
            name = this.name
        )
    }

    data class SignupResponseDto(
        val id: String,
        val username: String,
        val name: String
    ) {
        companion object {
            fun from(info: SignupService.SignupInfo) = SignupResponseDto(
                id = info.id,
                username = info.username,
                name = info.name
            )
        }
    }
}