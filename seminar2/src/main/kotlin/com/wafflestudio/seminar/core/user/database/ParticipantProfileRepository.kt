package com.wafflestudio.seminar.core.user.database


import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantProfileRepository : JpaRepository<ParticipantProfileEntity, Long> {
    
//    fun findByEmail(email: String): UserEntity?
//    fun existsByEmail(email: String): Boolean
}