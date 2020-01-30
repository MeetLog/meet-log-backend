package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Accounts
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class Account(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<Account>(Accounts)

    var snsType by Accounts.snsType
    var snsId by Accounts.snsId
}