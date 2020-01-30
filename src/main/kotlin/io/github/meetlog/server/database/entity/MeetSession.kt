package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Logs
import io.github.meetlog.server.database.table.MeetSessionUsers
import io.github.meetlog.server.database.table.MeetSessions
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class MeetSession(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<MeetSession>(MeetSessions)

    var participants by User via MeetSessionUsers
    var date by MeetSessions.date
    var place by MeetSessions.place
    val logs by Log referrersOn Logs.session
}
