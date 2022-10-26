package com.wafflestudio.seminar.core.user.database

import com.wafflestudio.seminar.common.BaseTimeEntity
import com.wafflestudio.seminar.core.userseminar.database.UserSeminarEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "instructorprofiles")
class InstructorProfileEntity(
    @Column(nullable = false)
    var username: String,
    @Column(unique = true, nullable = false)
    val email: String,
    @Column(nullable = false)
    var password: String,
    @OneToOne(mappedBy = "instructorProfileEntity")
    var userEntity: UserEntity,
    @CreationTimestamp
    override var createdAt: LocalDateTime? = LocalDateTime.now(),
    @CreationTimestamp
    override var modifiedAt: LocalDateTime? = createdAt
) : BaseTimeEntity() {


    

}