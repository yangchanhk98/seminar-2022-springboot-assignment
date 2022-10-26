package com.wafflestudio.seminar.core.user.api

import com.wafflestudio.seminar.common.Authenticated
import com.wafflestudio.seminar.common.UserContext
import com.wafflestudio.seminar.core.user.api.request.UserDto
import com.wafflestudio.seminar.core.user.service.AuthToken
import com.wafflestudio.seminar.core.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/api/v1/signup")
    fun signUp(
        @RequestBody req: UserDto.SignUpRequest
    ): AuthToken {
//        TODO("회원가입을 구현해주세요.")
        return userService.signUp(req.email, req.username, req.password)
    }

    @PostMapping("/api/v1/signin")
    fun logIn(
        @RequestHeader(value = "email") email: String,
        @RequestHeader(value = "password") password: String
    ): AuthToken {
//        TODO("회원가입을 진행한 유저가 로그인할 경우, JWT를 생성해서 내려주세요.")
        return userService.logIn(email, password)
    }

    @Authenticated
    @GetMapping("/api/v1/me")
    fun getMe(
        @RequestHeader(value = "Authorization") authorization: String,
        @UserContext userId: Long
    ) : UserDto.Response {
//        TODO("인증 토큰을 바탕으로 유저 정보를 적당히 처리해서, 본인이 잘 인증되어있음을 알려주세요.")
        return userService.getMe(userId)
    }

}