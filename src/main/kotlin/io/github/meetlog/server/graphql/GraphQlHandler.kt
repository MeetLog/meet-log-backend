package io.github.meetlog.server.graphql

import io.github.meetlog.server.database.DatabaseRepository
import io.github.meetlog.server.database.entity.AccountEntity
import io.github.meetlog.server.database.entity.FriendSessionEntity
import io.github.meetlog.server.database.entity.MeetSessionEntity
import io.github.meetlog.server.database.entity.UserEntity
import io.github.meetlog.server.exception.clientError

internal class GraphQlHandler {
    // ========== Query ===========
    fun getSession(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSessionEntity {
        return DatabaseRepository.getFriendSession(
            myId,
            friendsId,
            meetSessionId
        ) ?: clientError("Could not get FriendSession. One or more items may not have been not found.")
    }

    // ========== Mutation ============
    fun removeAccount(
        userId: String,
        accountId: String
    ): UserEntity {
        return DatabaseRepository.removeAccount(
            userId,
            accountId
        ) ?: clientError("")
    }

    fun createGroupSession(
        myId: String,
        date: String,
        place: String?
    ): MeetSessionEntity {
        return DatabaseRepository.createGroupSession(
            myId,
            date,
            place
        ) ?: clientError("Could not create MeetSession. One or more items may not have been not found.")
    }

    fun connectAccount(
        myId: String,
        friendsId: String,
        meetSession: String
    ): FriendSession {
        val session = DatabaseRepository.connectAccount(
            myId,
            friendsId,
            meetSession
        ) ?: clientError("Could not get FriendSession. One or more items may not have been not found.")

        return FriendSession(
            session.id.value.toString(),
            session.friend.accounts.toList(),
            session.session
        )
    }

    fun addAccount(
        userId: String,
        snsType: String,
        snsId: String
    ): AccountEntity {
        return DatabaseRepository.addAccount(
            userId,
            snsType,
            snsId
        ) ?: clientError("Could not add account. One or more items may not have been not found.")
    }

    fun postLog(
        sessionId: String,
        comments: String?,
        images: List<String>?
    ): Log {
        val log = DatabaseRepository.postLog(
            sessionId,
            comments,
            images
        ) ?: clientError("Could not post log. One or more items may not have been not found.")

        return Log(
            log.imageUrls.map { it.imageUrl },
            comments,
            log.id.value.toString()
        )
    }

    fun leaveMeetSession(
        meetSessionId: String,
        myId: String,
        endTime: String
    ) {
        DatabaseRepository.leaveMeetSession(
            meetSessionId,
            myId,
            endTime
        )
    }
}