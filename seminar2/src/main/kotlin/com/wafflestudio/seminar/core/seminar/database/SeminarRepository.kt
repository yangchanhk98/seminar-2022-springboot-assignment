package com.wafflestudio.seminar.core.seminar.database


import org.springframework.data.jpa.repository.JpaRepository

interface SeminarRepository : JpaRepository<SeminarEntity, Long> {
    
//    fun findByEmail(email: String): SeminarEntity?
//    fun existsByEmail(email: String): Boolean
}