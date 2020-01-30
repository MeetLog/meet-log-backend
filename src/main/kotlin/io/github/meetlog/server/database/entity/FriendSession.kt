package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.FriendSessionAccounts
import io.github.meetlog.server.database.table.FriendSessions
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class FriendSession(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<FriendSession>(FriendSessions)

    var accounts by Account via FriendSessionAccounts
    var session by MeetSession referencedOn FriendSessions.session
}