package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.AccountsTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class AccountEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountEntity>(AccountsTable)

    var snsType by AccountsTable.snsType
    var snsId by AccountsTable.snsId
    var user by UserEntity referencedOn AccountsTable.user
}