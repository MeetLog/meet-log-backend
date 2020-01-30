package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.UUIDTable

object Logs : UUIDTable() {
    val session = reference("session", MeetSessions)
    val comments = varchar("comments", 800)
}

object Images: UUIDTable() {
    val imageUrl = varchar("image_url", 2000)
    val log = reference("log", Logs)
}