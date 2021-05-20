package com.example.springlesson.domain.repository

import com.example.springlesson.domain.model.User

interface UserRepository {
    fun find(email: String): User?
    fun findAll(): List<User>?
}