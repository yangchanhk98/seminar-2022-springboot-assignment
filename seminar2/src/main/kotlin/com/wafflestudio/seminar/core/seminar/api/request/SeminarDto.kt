package com.wafflestudio.seminar.core.seminar.api.request

import java.time.LocalDateTime

class SeminarDto {
    
    data class SignUpRequest(
        val email: String,
        val username: String,
        val password: String,
    )
    
    data class Response(
        val id: Long,
        val username: String,
        val email: String,
        val lastLogin: LocalDateTime?
    )
    
}

