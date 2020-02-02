package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.ImagesTable
import io.github.meetlog.server.database.table.LogsTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class LogEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LogEntity>(LogsTable)

    val imageUrls by ImageEntity referrersOn ImagesTable.log
    var comments by LogsTable.comments
    var session by MeetSessionEntity referencedOn LogsTable.session
}

class ImageEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ImageEntity>(ImagesTable)

    var imageUrl by ImagesTable.imageUrl
    var log by LogEntity referencedOn ImagesTable.log
}