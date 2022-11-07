package com.wafflestudio.seminar.core.profile.database

import com.wafflestudio.seminar.common.BaseTimeEntity
import com.wafflestudio.seminar.core.user.database.UserEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "participant_profile")
class ParticipantProfileEntity(
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seminar_user_id")
    val user: UserEntity? = null
) : BaseTimeEntity() {}

