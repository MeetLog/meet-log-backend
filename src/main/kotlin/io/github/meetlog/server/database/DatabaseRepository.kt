package io.github.meetlog.server.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.meetlog.server.config.DatabaseConfig
import io.github.meetlog.server.database.entity.FriendSession
import io.github.meetlog.server.database.entity.User
import io.github.meetlog.server.database.table.Accounts
import io.github.meetlog.server.database.table.FriendSessions
import io.github.meetlog.server.database.table.Logs
import io.github.meetlog.server.database.table.MeetSessions
import io.github.meetlog.server.database.table.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseRepository {
    private lateinit var database: Database

    fun init(config: DatabaseConfig) {
        val hikariConfig = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://${config.host}:${config.port}/${config.password}" //TODO set jdbc url
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
        val user = transaction(database) {
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
        val idInt = id?.let {
            try {
                it.toInt()
            }catch (ex: NumberFormatException) {
                return null
            }
        } ?: return null

        return transaction(database) {
            User.findById(idInt)
        }
    }

    fun getSession(
        myId: String,
        friendsId: String,
        meetSessionId: String
    ): FriendSession {
        TODO()
    }
}