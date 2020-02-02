package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable

object LogsTable : IntIdTable() {
    val session = reference("session", MeetSessionsTable)
    val comments = varchar("comments", 800).nullable()
}

object ImagesTable : IntIdTable() {
    val imageUrl = varchar("image_url", 2000)
    val log = reference("log", LogsTable)
}