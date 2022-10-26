package com.wafflestudio.seminar.core.userseminar.database

import com.wafflestudio.seminar.common.BaseTimeEntity
import com.wafflestudio.seminar.core.seminar.database.SeminarEntity
import com.wafflestudio.seminar.core.user.database.UserEntity
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "userseminars")
class UserSeminarEntity(
    @Column(nullable = false)
    var joinedAt: LocalDateTime,
    var isActive: Boolean,
    var droppedAt: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    val userEntity: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    val seminarEntity: SeminarEntity,
    @CreationTimestamp
    override var createdAt: LocalDateTime? = LocalDateTime.now(),
    @CreationTimestamp
    override var modifiedAt: LocalDateTime? = createdAt
) : BaseTimeEntity() {


    

}