package com.example.springlesson.application.service.security

import com.example.springlesson.application.service.AuthenticationService
import com.example.springlesson.domain.enum.RoleType
import com.example.springlesson.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

//ユーザーが入力した情報と下記で取得したデータとを照合し、ログイン認証を行う
class SpringSecurityUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = authenticationService.findUser(username)
        return user?.let { SpringSecurityUserDetails(user) }
    }
}

//以下でログイン認証に必要な照合用のデータ(email,password等)を取得しクラスに格納する
data class SpringSecurityUserDetails(
    val id: Long,
    val email: String,
    val pass: String,
    val name: String,
    val roleType: RoleType
) : UserDetails {
    constructor(user: User) : this(user.id, user.email, user.password, user.name, user.roleType)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(this.roleType.toString())
    }

    override fun getPassword(): String {
        return this.pass
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean { //ユーザーのアカウントの有効期限
        return true
    }

    override fun isAccountNonLocked(): Boolean { //ロックされたアカウントかどうか
        return true
    }

    override fun isCredentialsNonExpired(): Boolean { //パスワードの期限切れ
        return true
    }

    override fun isEnabled(): Boolean { //有効なユーザーかどうか
        return true
    }
}