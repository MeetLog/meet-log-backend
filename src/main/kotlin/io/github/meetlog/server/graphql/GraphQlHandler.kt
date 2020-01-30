package io.github.meetlog.server.graphql

import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.database.entity.FriendSession

internal class GraphQlHandler {
    // ========== Query ===========
    fun getSession(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSession {

    }
}