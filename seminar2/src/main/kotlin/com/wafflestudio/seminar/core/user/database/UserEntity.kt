package com.wafflestudio.seminar.core.user.database

import com.wafflestudio.seminar.common.BaseTimeEntity
import com.wafflestudio.seminar.core.userseminar.database.UserSeminarEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false)
    var username: String,
    @Column(unique = true, nullable = false)
    val email: String,
    @Column(nullable = false)
    var password: String,
    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.REMOVE])
    var userSeminarEntities: MutableList<UserSeminarEntity> = mutableListOf(),
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_profile")
    var participantProfileEntity: ParticipantProfileEntity? = null,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_profile")
    var instructorProfileEntity: InstructorProfileEntity? = null,
    @CreationTimestamp
    override var createdAt: LocalDateTime? = LocalDateTime.now(),
    @CreationTimestamp
    override var modifiedAt: LocalDateTime? = createdAt
) : BaseTimeEntity() {


    

}