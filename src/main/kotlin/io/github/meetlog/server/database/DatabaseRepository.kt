package io.github.meetlog.server.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.meetlog.server.config.DatabaseConfig
import io.github.meetlog.server.database.entity.Account
import io.github.meetlog.server.database.entity.FriendSession
import io.github.meetlog.server.database.entity.Image
import io.github.meetlog.server.database.entity.Log
import io.github.meetlog.server.database.entity.MeetSession
import io.github.meetlog.server.database.entity.MeetSessionEndTime
import io.github.meetlog.server.database.entity.User
import io.github.meetlog.server.database.table.Accounts
import io.github.meetlog.server.database.table.FriendSessions
import io.github.meetlog.server.database.table.Logs
import io.github.meetlog.server.database.table.MeetSessions
import io.github.meetlog.server.database.table.Users
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
            Accounts,
            FriendSessions,
            Logs,
            MeetSessions,
            Users
        )
    }

    fun registerUser(
        name: String,
        password: String,
        nfcIdm: Long,
        iconUrl: String
    ): Int {
        val user = transaction {
            User.new {
                this.name = name
                this.password = password
                this.nfcIdm = nfcIdm
                this.iconUrl = iconUrl
            }
        }

        return user.id.value
    }

    fun getUser(id: String?): User? {
        return get(id, User)
    }

    fun getFriendSession(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSession? {
        return transaction {
            val me = getUser(myId) ?: return@transaction null
            val friend = getUser(friendsId) ?: return@transaction null

            val meetSession = get(meetSessionId, MeetSession) ?: return@transaction null

            val session = transaction {
                FriendSession.find {
                    (FriendSessions.session eq meetSession.id)
                        .and(FriendSessions.me eq me.id)
                        .and(FriendSessions.friend eq friend.id)
                }
            }.singleOrNull()

            if(session != null) return@transaction session

            FriendSession.new {
                this.me = me
                this.friend = friend
                this.session = meetSession
            }
        }
    }

    fun removeAccount(
        myId: String,
        accountId: String
    ): User? {
        return transaction{
            val account = get(accountId, Account) ?: return@transaction null

            val me = getUser(myId) ?: return@transaction null
            return@transaction if(me.accounts.contains(account)) {
                account.delete()
                me
            }else{
                null
            }
        }
    }

    fun createGroupSession(
        myId: String,
        date: String,
        place: String?
    ): MeetSession? {
        return transaction {
            val me = getUser(myId) ?: return@transaction null

            MeetSession.new {
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
    ): FriendSession? {
        return transaction {
            val me = getUser(myId) ?: return@transaction null
            val friend = getUser(friendsId) ?: return@transaction null
            val meetSession = get(meetSessionId, MeetSession) ?: return@transaction null

            FriendSession.new {
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
    ): Account? {
        return transaction {
            val user = getUser(userId) ?: return@transaction null

            Account.new {
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
    ): Log? {
        val log = transaction {
            val session = get(sessionId, MeetSession) ?: return@transaction null

            Log.new {
                this.session = session
                this.comments = comments
            }
        } ?: return null

        if(images != null) {
            transaction {
                for(image in images) {
                    Image.new {
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
            val meetSession = get(meetSessionId, MeetSession) ?: return@transaction false
            val me = getUser(myId) ?: return@transaction false
            val time = DateTime.parse(endTime)

            MeetSessionEndTime.new {
                this.user = me
                this.session = session
                this.endTime = time
            }

            return@transaction true
        }
    }

    private fun <T: Entity<Int>> get(id: String?, entity: EntityClass<Int, T>): T? {
        val idInt = id?.let {
            try {
                it.toInt()
            }catch (ex: NumberFormatException) {
                return null
            }
        } ?: return null

        return entity.findById(idInt)
    }
}