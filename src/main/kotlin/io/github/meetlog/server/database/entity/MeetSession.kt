package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.Logs
import io.github.meetlog.server.database.table.MeetSessionEndTimes
import io.github.meetlog.server.database.table.MeetSessionUsers
import io.github.meetlog.server.database.table.MeetSessions
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class MeetSession(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<MeetSession>(MeetSessions)

    var participants by User via MeetSessionUsers
    var date by MeetSessions.date
    var startTime by MeetSessions.startTime
    val endTime by MeetSessionEndTime referrersOn MeetSessionEndTimes.session
    var place by MeetSessions.place
    val logs by Log referrersOn Logs.session
}

class MeetSessionEndTime(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<MeetSessionEndTime>(MeetSessionEndTimes)

    var session by MeetSession referencedOn MeetSessionEndTimes.session
    var user by User referencedOn MeetSessionEndTimes.user
    var endTime by MeetSessionEndTimes.endTime
}