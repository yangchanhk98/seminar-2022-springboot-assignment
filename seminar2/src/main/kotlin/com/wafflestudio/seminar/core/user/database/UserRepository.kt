package com.wafflestudio.seminar.core.user.database

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): Optional<UserEntity>
    fun findTopByOrderByIdDesc(): UserEntity?
}