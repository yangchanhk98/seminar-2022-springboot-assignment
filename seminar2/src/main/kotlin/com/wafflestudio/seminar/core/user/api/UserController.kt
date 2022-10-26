package com.wafflestudio.seminar.core.user.api

import com.wafflestudio.seminar.common.Authenticated
import com.wafflestudio.seminar.common.LogExecutionTime
import com.wafflestudio.seminar.common.UserContext
import com.wafflestudio.seminar.core.user.api.request.RegisterParticipantRequest
import com.wafflestudio.seminar.core.user.api.response.UserProfile
import com.wafflestudio.seminar.core.user.database.UserEntity
import com.wafflestudio.seminar.core.user.service.AuthException
import com.wafflestudio.seminar.core.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
class UserController(
    private val userService: UserService
) {
    
    @LogExecutionTime
    @Authenticated
    @GetMapping("/api/v1/user/{userId}")
    fun getUser(
        @PathVariable userId: Long
    ) = ResponseEntity.ok(userService.getUser(userId))
    
    @LogExecutionTime
    @Authenticated
    @GetMapping("/api/v1/users")
    fun getUsers() = ResponseEntity.ok(userService.getAllUsers())
    
    @LogExecutionTime
    @Authenticated
    @PostMapping("/api/v1/user/participant")
    fun registerParticipantForInstructor(
        @UserContext user: Optional<UserEntity>, @Valid @RequestBody request: RegisterParticipantRequest
    ) : ResponseEntity<UserProfile> {
        if (user.isEmpty) {
            throw AuthException("유저를 찾을 수 없습니다")
        }
        return ResponseEntity.ok(userService.registerParticipantForInstructor(user.get(), request))
    }
    
    @LogExecutionTime
    @Authenticated
    @DeleteMapping("/api/v1/user")
    fun deleteUser(
        @UserContext user: Optional<UserEntity>
    ) : ResponseEntity<Map<String, Long>>{
        if (user.isEmpty) {
            throw AuthException("유저를 찾을 수 없습니다")
        }
        userService.deleteUser(user.get())
        return ResponseEntity.ok(
            mapOf(
                "deleted_user_id" to user.get().id
            )
        )
    }
}