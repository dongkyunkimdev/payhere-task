package com.example.payhere.ledger.domain

import com.example.payhere.common.entity.JpaAuditEntity
import com.example.payhere.ledger.application.UpdateLedgerService
import com.example.payhere.ledger.domain.exception.AlreadyDeletedException
import com.example.payhere.ledger.domain.exception.IsNotDeletedException
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

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: User = user
        protected set

    fun update(command: UpdateLedgerService.UpdateLedgerCommand) {
        price = command.price
        memo = command.memo
    }

    fun delete() {
        if (isDeleted) {
            throw AlreadyDeletedException(getId())
        }

        isDeleted = true
    }

    fun restore() {
        if (!isDeleted) {
            throw IsNotDeletedException(getId())
        }

        isDeleted = false
    }
}