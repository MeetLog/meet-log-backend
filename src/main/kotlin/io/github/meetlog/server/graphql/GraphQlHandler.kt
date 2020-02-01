package io.github.meetlog.server.graphql

import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.database.entity.FriendSessionEntity
import io.github.meetlog.server.database.entity.UserEntity
import io.github.meetlog.server.exception.clientError

internal class GraphQlHandler {
    // ========== Query ===========
    fun getSession(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSession {

    }
}