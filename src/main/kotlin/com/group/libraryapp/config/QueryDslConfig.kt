package com.group.libraryapp.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

/**
 * queryDsl 설정.
 */
@Configuration
class QueryDslConfig(
    private val em: EntityManager
) {

    @Bean
    fun queryDsl(): JPAQueryFactory {
        return JPAQueryFactory(em)
    }
}