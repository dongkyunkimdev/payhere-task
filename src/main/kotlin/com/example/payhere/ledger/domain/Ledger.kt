package com.example.payhere.ledger.domain

import com.example.payhere.common.entity.JpaAuditEntity
import com.example.payhere.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "ledgers")
class Ledger(
    price: Long,
    memo: String,
    user: User
) : JpaAuditEntity() {
    @Column(name = "price", nullable = false)
    var price: Long = price
        protected set

    @Column(name = "memo", nullable = false)
    var memo: String = memo
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: User = user
        protected set
}