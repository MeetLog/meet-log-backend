package io.github.meetlog.server.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.meetlog.server.config.DatabaseConfig
import io.github.meetlog.server.database.entity.AccountEntity
import io.github.meetlog.server.database.entity.FriendSessionEntity
import io.github.meetlog.server.database.entity.ImageEntity
import io.github.meetlog.server.database.entity.LogEntity
import io.github.meetlog.server.database.entity.MeetSessionEndTimeEntity
import io.github.meetlog.server.database.entity.MeetSessionEntity
import io.github.meetlog.server.database.entity.UserEntity
import io.github.meetlog.server.database.table.AccountsTable
import io.github.meetlog.server.database.table.FriendSessionsTable
import io.github.meetlog.server.database.table.LogsTable
import io.github.meetlog.server.database.table.MeetSessionsTable
import io.github.meetlog.server.database.table.UsersTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object DatabaseRepository {
    private lateinit var database: Database

    fun init(config: DatabaseConfig) {
        val hikariConfig = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://${config.host}:${config.port}/${config.dataBaseName}"
            username = config.user
            password = config.password

            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }

        val dataSource = HikariDataSource(hikariConfig)
        database = Database.connect(dataSource)
        SchemaUtils.create(
            AccountsTable,
            FriendSessionsTable,
            LogsTable,
            MeetSessionsTable,
            UsersTable
        )
    }

    fun registerUser(
        name: String,
        password: String,
        nfcIdm: Long,
        iconUrl: String
    ): Int {
        val user = transaction {
            UserEntity.new {
                this.name = name
                this.password = password
                this.nfcIdm = nfcIdm
                this.iconUrl = iconUrl
            }
        }

        return user.id.value
    }

    fun getUser(id: String?): UserEntity? {
        return get(id, UserEntity)
    }

    fun getFriendSession(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSessionEntity? {
        return transaction {
            val me = getUser(myId) ?: return@transaction null
            val friend = getUser(friendsId) ?: return@transaction null

            val meetSession = get(meetSessionId, MeetSessionEntity) ?: return@transaction null

            val session = transaction {
                FriendSessionEntity.find {
                    (FriendSessionsTable.session eq meetSession.id)
                        .and(FriendSessionsTable.me eq me.id)
                        .and(FriendSessionsTable.friend eq friend.id)
                }
            }.singleOrNull()

            if (session != null) return@transaction session

            FriendSessionEntity.new {
                this.me = me
                this.friend = friend
                this.session = meetSession
            }
        }
    }

    fun removeAccount(
        myId: String,
        accountId: String
    ): UserEntity? {
        return transaction {
            val account = get(accountId, AccountEntity) ?: return@transaction null

            val me = getUser(myId) ?: return@transaction null
            return@transaction if (me.accounts.contains(account)) {
                account.delete()
                me
            } else {
                null
            }
        }
    }

    fun createGroupSession(
        myId: String,
        date: String,
        place: String?
    ): MeetSessionEntity? {
        return transaction {
            val me = getUser(myId) ?: return@transaction null

            MeetSessionEntity.new {
                this.date = DateTime.parse(date)
                this.place = place
                this.participants = SizedCollection(me)
            }
        }
    }

    fun connectAccount(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSessionEntity? {
        return transaction {
            val me = getUser(myId) ?: return@transaction null
            val friend = getUser(friendsId) ?: return@transaction null
            val meetSession = get(meetSessionId, MeetSessionEntity) ?: return@transaction null

            FriendSessionEntity.new {
                this.session = meetSession
                this.me = me
                this.friend = friend
            }
        }
    }

    fun addAccount(
        userId: String,
        snsType: String,
        snsId: String
    ): AccountEntity? {
        return transaction {
            val user = getUser(userId) ?: return@transaction null

            AccountEntity.new {
                this.snsType = snsType
                this.snsId = snsId
                this.user = user
            }
        }
    }

    fun postLog(
        sessionId: String,
        comments: String?,
        images: List<String>?
    ): LogEntity? {
        val log = transaction {
            val session = get(sessionId, MeetSessionEntity) ?: return@transaction null

            LogEntity.new {
                this.session = session
                this.comments = comments
            }
        } ?: return null

        if (images != null) {
            transaction {
                for (image in images) {
                    ImageEntity.new {
                        this.imageUrl = image
                        this.log = log
                    }
                }
            }
        }

        return log
    }

    fun leaveMeetSession(
        meetSessionId: String,
        myId: String,
        endTime: String
    ): Boolean {
        return transaction {
            val meetSession = get(meetSessionId, MeetSessionEntity) ?: return@transaction false
            val me = getUser(myId) ?: return@transaction false
            val time = DateTime.parse(endTime)

            MeetSessionEndTimeEntity.new {
                this.user = me
                this.session = session
                this.endTime = time
            }

            return@transaction true
        }
    }

    private fun <T : Entity<Int>> get(id: String?, entity: EntityClass<Int, T>): T? {
        val idInt = id?.let {
            try {
                it.toInt()
            } catch (ex: NumberFormatException) {
                return null
            }
        } ?: return null

        return entity.findById(idInt)
    }
}