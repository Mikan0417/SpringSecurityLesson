package com.example.springlesson.domain.model

import com.example.springlesson.domain.enum.RoleType

data class User(
    var id: Long,
    var email: String,
    var password: String,
    var name: String,
    var roleType: RoleType
)
