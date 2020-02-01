package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable

object FriendSessions : IntIdTable() {
    val me = reference("me", Users)
    val friend = reference("friend", Users)
    val session  = reference("session", MeetSessions)
}