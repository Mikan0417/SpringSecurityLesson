package com.example.springlesson.application.service

import com.example.springlesson.domain.model.User
import com.example.springlesson.domain.repository.UserRepository
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUser(): List<User> {
        return userRepository.findAll() ?: throw IllegalStateException("error")
    }
}