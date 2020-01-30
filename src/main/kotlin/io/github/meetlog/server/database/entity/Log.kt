package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Images
import io.github.meetlog.server.database.table.Logs
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Log(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Log>(Logs)

    val imageUrls by Image referrersOn Images.log
    var comments by Logs.comments
}

class Image(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Image>(Images)

    var imageUrl by Images.imageUrl
}