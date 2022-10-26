package com.wafflestudio.seminar.core.user.service

import com.wafflestudio.seminar.common.Seminar400
import com.wafflestudio.seminar.common.Seminar401
import com.wafflestudio.seminar.common.Seminar404
import com.wafflestudio.seminar.common.Seminar409
import com.wafflestudio.seminar.config.AuthConfig
import com.wafflestudio.seminar.core.user.api.request.UserDto
import com.wafflestudio.seminar.core.user.database.UserEntity
import com.wafflestudio.seminar.core.user.database.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface UserService {
    fun signUp(email: String, username: String, password: String): AuthToken
    fun logIn(email: String, password: String): AuthToken
    fun getMe(userId: Long): UserDto.Response
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val authConfig: AuthConfig,
    private val authTokenService: AuthTokenService
) : UserService {

    @Transactional
    override fun signUp(email: String, username: String, password: String): AuthToken {
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw Seminar400("이름/이메일을 모두 입력해주세요.")
        } else if (userRepository.existsByEmail(email)) {
            throw Seminar409("이미 존재하는 이메일입니다.")
        } else {
            val authToken : AuthToken = authTokenService.generateTokenByUsername(username)
            userRepository.save(
                UserEntity(
                    username,
                    email,
                    authConfig.passwordEncoder().encode(password)
                )
            )
            return authToken
        }
    }

    @Transactional
    override fun logIn(email: String, password: String): AuthToken {
        val entity = userRepository.findByEmail(email)
        if (entity == null) {
            throw Seminar404("존재하지 않는 이메일입니다.")
        } else if (!authConfig.passwordEncoder().matches(password, entity.password)) {
            throw Seminar401("비밀번호가 틀립니다.")
        } else {
            val modifiedToken : AuthToken = authTokenService.generateTokenByEmail(entity.email)
            entity.modifiedAt = LocalDateTime.now()
            return modifiedToken
        }
    }

    @Transactional
    override fun getMe(userId: Long) : UserDto.Response {
//        TODO("Not yet implemented")
        val entity = userRepository.findById(userId)
        if (entity.isEmpty) {
            throw Seminar404("존재하지 않는 회원입니다.")
        }
        return UserDto.Response(userId, entity.get().username, entity.get().email, entity.get().modifiedAt)
    }
}