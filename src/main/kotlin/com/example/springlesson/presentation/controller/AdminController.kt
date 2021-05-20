package com.example.springlesson.presentation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("admin")
class AdminController {
    @GetMapping("top")
    fun adminTop() :String {
        return "admin_top"
    }
}