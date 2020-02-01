package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable

object AccountsTable : IntIdTable() {
    // NOTE: lengthは割と適当
    val snsType = varchar("sns_type", 15)
    // NOTE: これも適当。Twitterはintに収まるらしいけど他のSNSにも対応するため
    val snsId = varchar("sns_id", 50)

    val user = reference("user_id", UsersTable)
}