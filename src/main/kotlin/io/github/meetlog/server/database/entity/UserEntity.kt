package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.AccountsTable
import io.github.meetlog.server.database.table.UsersTable
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class UserEntity(id: EntityID<Int>): IntEntity(id), Principal {
    companion object: IntEntityClass<UserEntity>(UsersTable)

    var name by UsersTable.name
    var password by UsersTable.password
    var nfcIdm by UsersTable.nfcIdm
    var iconUrl by UsersTable.iconUrl
    val accounts by AccountEntity referrersOn  AccountsTable.user
}
