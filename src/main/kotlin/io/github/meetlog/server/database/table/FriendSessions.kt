package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Table

object FriendSessions : IntIdTable() {
    val session  = reference("session", MeetSessions)
}

object FriendSessionAccounts: Table() {
    val account = reference("account", Accounts).primaryKey(0)
    val session = reference("session", FriendSessions).primaryKey(1)
}