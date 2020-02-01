package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.FriendSessionsTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class FriendSessionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<FriendSessionEntity>(FriendSessionsTable)

    var me by UserEntity referencedOn FriendSessionsTable.me
    var friend by UserEntity referencedOn FriendSessionsTable.friend
    var session by MeetSessionEntity referencedOn FriendSessionsTable.session
}