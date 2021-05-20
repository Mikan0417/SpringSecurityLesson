package com.example.springlesson.presentation.controller

import com.example.springlesson.application.service.AuthenticationService
import com.example.springlesson.application.service.UserService
import com.example.springlesson.presentation.form.AuthenticationInformation
import com.example.springlesson.presentation.form.LoginInfo
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("login")
class LoginController(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) {
    @GetMapping("") //ログインページを表示するためのfunction
    fun loginPage(model: Model) :String {
        model.addAttribute("authInfo", AuthenticationInformation("", "")) //ログイン認証に必要なデータ(email, pass)をhtml側へ渡し、"authInfo"で利用できるようにする
        return "login" //ログインページを返す
    }


    @GetMapping("/userlist") //ユーザーリストページを表示するためのfunction
    fun logintouserlist(model: Model) :String {
        val userList=userService.getAllUser()
        model.addAttribute("userlist", userList)
        return "userlist" //ユーザーリストページを返す
    }
}

