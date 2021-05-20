package com.example.springlesson.presentation.config

import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
//通常アプリケーションに保存されるセッションをRedisに保存する
@EnableRedisHttpSession //セッションタイムアウトの時間(maxInactiveIntervalInSeconds = 60)　デフォルトは30分
class HttpSessionConfig {
    @Bean
    fun connectionFactory(): JedisConnectionFactory {
        return JedisConnectionFactory()
    }
}