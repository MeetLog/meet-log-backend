package io.github.meetlog.server.graphql

import io.github.meetlog.server.database.entity.AccountEntity
import io.github.meetlog.server.database.entity.MeetSessionEntity

data class FriendSession(
    val id: String,
    val accounts: List<AccountEntity>,
    val session: MeetSessionEntity
)

data class Log(
    val imageUrl: List<String>,
    val comments: String?,
    val id: String
)