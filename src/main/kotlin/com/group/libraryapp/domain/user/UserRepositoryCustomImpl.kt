package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.user.QUser.user
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): UserRepositoryCustom {

    /**
     * @ref UserRepository.kt, findAllWithHistories(): List<User>
     */
    override fun findAllWithHistories(): List<User> {
        return queryFactory.select(user)//select *
            .distinct()
            .from(user) // from user
            .leftJoin(userLoanHistory) //left join user_loan_history
            .on(userLoanHistory.user.id.eq(user.id)) //user.user_id == user_loan_history.user_id
            .fetchJoin() //앞의 join을 fetch join으로 간주
            .fetch() //쿼리 실행
    }
}