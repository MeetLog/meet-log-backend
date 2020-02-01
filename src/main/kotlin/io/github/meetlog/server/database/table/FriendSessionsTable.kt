package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable

object FriendSessionsTable : IntIdTable() {
    val me = reference("me", UsersTable)
    val friend = reference("friend", UsersTable)
    val session  = reference("session", MeetSessionsTable)
}