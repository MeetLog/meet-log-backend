package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.FriendSessions
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class FriendSession(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<FriendSession>(FriendSessions)

    var me by User referencedOn FriendSessions.me
    var friend by User referencedOn FriendSessions.friend
    var session by MeetSession referencedOn FriendSessions.session
}