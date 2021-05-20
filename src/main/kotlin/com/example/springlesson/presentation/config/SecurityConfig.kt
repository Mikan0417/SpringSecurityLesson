package com.example.springlesson.presentation.config

import com.example.springlesson.application.service.AuthenticationService
import com.example.springlesson.application.service.security.SpringSecurityUserDetailsService
import com.example.springlesson.domain.enum.RoleType
import com.example.springlesson.presentation.handler.SpringSecurityAccessDeniedHandler
import com.example.springlesson.presentation.handler.SpringSecurityAuthenticationEntryPoint
import com.example.springlesson.presentation.handler.SpringSecurityAuthenticationFailureHandler
import com.example.springlesson.presentation.handler.SpringSecurityAuthenticationSuccessHandler
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
class SecurityConfig(private val authenticationService: AuthenticationService) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .mvcMatchers("/login").permitAll() //.permitAllを指定し、権限なしでアクセス許可
            .mvcMatchers("admin/**").hasAuthority(RoleType.ADMIN.toString()) //.hasAuthority(RoleType.ADMIN~を指定し、ADMIN権限のみアクセス許可
            .mvcMatchers("/login/userlist").hasAuthority(RoleType.USER.toString()) //.hasAuthority(RoleType.USER~を指定し、USER権限のみアクセス許可
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .formLogin() //ユーザー名(今回はemail)、パスワードでのログインを有効化
            .loginProcessingUrl("/login/auth") //ログイン認証の実行のリクエスト
            .loginPage("/login")
            .usernameParameter("email") //ログインで使用するユーザー名のパラメーターをemailに設定
            .passwordParameter("pass") //ログインで使用するパスワードのパラメーターをpassに設定
            .successHandler(SpringSecurityAuthenticationSuccessHandler()) //認証成功時に実行するハンドラー
            .failureHandler(SpringSecurityAuthenticationFailureHandler()) //認証失敗時に実行するハンドラー
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(SpringSecurityAuthenticationEntryPoint()) //未認証時に実行するハンドラー
            .accessDeniedHandler(SpringSecurityAccessDeniedHandler()) //認可失敗時に実行するハンドラー
            .and().logout() //ログアウト機能
            .logoutUrl("/user/logout") //ログアウト実行のリクエスト
            .logoutSuccessUrl("/login") //ログアウト後の遷移先
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(SpringSecurityUserDetailsService(authenticationService)).passwordEncoder(BCryptPasswordEncoder())
    }
}