package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable

object Logs : IntIdTable() {
    val session = reference("session", MeetSessions)
    val comments = varchar("comments", 800).nullable()
}

object Images: IntIdTable() {
    val imageUrl = varchar("image_url", 2000)
    val log = reference("log", Logs)
}