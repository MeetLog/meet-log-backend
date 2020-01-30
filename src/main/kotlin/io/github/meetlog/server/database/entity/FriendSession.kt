package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.FriendSessionAccounts
import io.github.meetlog.server.database.table.FriendSessions
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class FriendSession(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<FriendSession>(FriendSessions)

    var accounts by Account via FriendSessionAccounts
    var session by MeetSession referencedOn FriendSessions.session
}