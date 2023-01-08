package com.example.payhere.ledger.infrastructure.persistence.quqerydsl

import com.example.payhere.ledger.domain.Ledger
import com.example.payhere.ledger.domain.QLedger
import com.example.payhere.user.domain.QUser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class LedgerRepositorySupport(
    private val queryFactory: JPAQueryFactory
) {
    fun findLedgerById(id: String): Ledger? =
        queryFactory.selectFrom(QLedger.ledger)
            .where(QLedger.ledger.id.eq(id))
            .leftJoin(QLedger.ledger.user, QUser.user)
            .fetchJoin()
            .fetchOne()

    fun findAllLedgerByUserId(id: String): List<Ledger> =
        queryFactory.selectFrom(QLedger.ledger)
            .where(QLedger.ledger.user.id.eq(id))
            .leftJoin(QLedger.ledger.user, QUser.user)
            .fetchJoin()
            .fetch()
}