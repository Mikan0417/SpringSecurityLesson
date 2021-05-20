package com.example.springlesson.infrastructure.database.repository

import com.example.springlesson.domain.model.User
import com.example.springlesson.domain.repository.UserRepository
import com.example.springlesson.infrastructure.database.mapper.UserDynamicSqlSupport
import com.example.springlesson.infrastructure.database.mapper.UserMapper
import com.example.springlesson.infrastructure.database.mapper.selectOne
import com.example.springlesson.infrastructure.database.record.UserRecord
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.springframework.stereotype.Repository
import org.mybatis.dynamic.sql.SqlBuilder.select
import org.mybatis.dynamic.sql.render.RenderingStrategy

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class UserRepositoryImpl(
    private val mapper: UserMapper
) : UserRepository {
    override fun find(email: String): User? {
        val record = mapper.selectOne {
            where(UserDynamicSqlSupport.User.email, isEqualTo(email))
        }

        return record?.let { toModel(it)}
    }

    override fun findAll(): List<User>? {
        val selectStatement = select(UserDynamicSqlSupport.User.allColumns()).from(UserDynamicSqlSupport.User).build().render(
            RenderingStrategy.MYBATIS3)
        val userRecordlist = mapper.selectMany(selectStatement)
        return userRecordlist?.map { toModel(it) }
    }

    private fun toModel(record: UserRecord): User{
        return User(record.id!!, record.email!!, record.password!!, record.name!!, record.roleType!!)
    }

}