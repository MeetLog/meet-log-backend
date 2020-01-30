package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Images
import io.github.meetlog.server.database.table.Logs
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import java.util.UUID

class Log(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<Log>(Logs)

    val imageUrls by Image referrersOn Images.log
    var comments by Logs.comments
}

class Image(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<Image>(Images)

    var imageUrl by Images.imageUrl
}