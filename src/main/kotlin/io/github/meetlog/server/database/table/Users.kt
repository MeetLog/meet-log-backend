package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.UUIDTable

object Users : UUIDTable() {
    val name = varchar("name", 30)
    val password = varchar("password", 60)

    val nfcIdm = long("nfcIdm")
    val iconUrl = varchar("icon_url", 2000).nullable()
}