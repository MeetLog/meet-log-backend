package io.github.meetlog.server.database.table

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Table

object MeetSessions : LongIdTable() {
    val date = date("date")
    val place = varchar("place", 50)
}

object MeetSessionUsers: Table() {
    val user = reference("user", Users).primaryKey(0)
    val session = reference("session", MeetSessions).primaryKey(1)
}

object MeetSessionLogs: Table() {
    val session = reference("session", MeetSessions).primaryKey(0)
    val log = reference("log", Logs).primaryKey(1)
}