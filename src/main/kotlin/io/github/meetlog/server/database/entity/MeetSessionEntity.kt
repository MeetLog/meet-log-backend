package io.github.meetlog.server.database.entity

import io.github.meetlog.server.database.table.LogsTable
import io.github.meetlog.server.database.table.MeetSessionEndTimesTable
import io.github.meetlog.server.database.table.MeetSessionUsersTable
import io.github.meetlog.server.database.table.MeetSessionsTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class MeetSessionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<MeetSessionEntity>(MeetSessionsTable)

    var participants by UserEntity via MeetSessionUsersTable
    var date by MeetSessionsTable.date
    var startTime by MeetSessionsTable.startTime
    val endTime by MeetSessionEndTimeEntity referrersOn MeetSessionEndTimesTable.session
    var place by MeetSessionsTable.place
    val logs by LogEntity referrersOn LogsTable.session
}

class MeetSessionEndTimeEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<MeetSessionEndTimeEntity>(MeetSessionEndTimesTable)

    var session by MeetSessionEntity referencedOn MeetSessionEndTimesTable.session
    var user by UserEntity referencedOn MeetSessionEndTimesTable.user
    var endTime by MeetSessionEndTimesTable.endTime
}