package com.example.springlesson.presentation.handler

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SpringSecurityAuthenticationSuccessHandler:
AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        //権限によってログイン成功時のリダイレクト先を指定
        response.status = HttpServletResponse.SC_OK
        val redirectStrategy = DefaultRedirectStrategy()
        val roles = AuthorityUtils.authorityListToSet(authentication.authorities)
        if (roles.contains("ADMIN")) {   //ADMIN権限の場合、"/admin/top"ページへリダイレクト
            redirectStrategy.sendRedirect(request, response, "/admin/top")
        } else if (roles.contains("USER")) {    //USER権限の場合、"/login/userlist"ページへリダイレクト
            redirectStrategy.sendRedirect(request, response, "/login/userlist")
        }
    }
}