package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Table

object MeetSessionsTable : IntIdTable() {
    val date = date("date")
    val place = varchar("place", 50).nullable()
    val startTime = datetime("start_time")
    val endTime = reference("end_time", MeetSessionEndTimesTable)
}

object MeetSessionUsersTable: Table() {
    val user = reference("user", UsersTable).primaryKey(0)
    val session = reference("session", MeetSessionsTable).primaryKey(1)
}

object MeetSessionEndTimesTable: IntIdTable() {
    val session = reference("session", MeetSessionsTable)
    val user = reference("user", UsersTable)
    val endTime = datetime("end")
}