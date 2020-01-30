package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Accounts
import io.github.meetlog.server.database.table.Users
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import java.util.UUID

class User(id: EntityID<UUID>): UUIDEntity(id), Principal {
    companion object: UUIDEntityClass<User>(Users)

    var name by Users.name
    var password by Users.password
    var nfcIdm by Users.nfcIdm
    var iconUrl by Users.iconUrl
    val accounts by Account referrersOn Accounts.user
}