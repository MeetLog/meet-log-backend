package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Accounts
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Account(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Account>(Accounts)

    var snsType by Accounts.snsType
    var snsId by Accounts.snsId
}