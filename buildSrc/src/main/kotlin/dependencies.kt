@file:Suppress("MemberVisibilityCanBePrivate")

/**
 * バージョン管理
 */
object Ver {
    const val kotlin = "1.3.70"
}

/**
 * Ktorのライブラリをまとめているobject
 *
 * @property version Ktorのバージョン
 * @property graphQlVersion GraphQLをKtorで簡単に扱うためのライブラリのバージョン
 * @property server Ktorサーバー
 * @property tests Ktorの単体テストライブラリ
 * @property jwt KtorをJWTによるトークン認証に対応させるライブラリ
 * @property graphQl GraphQL用ライブラリ
 */
object Ktor {
    const val version = "1.3.0-rc2"
    const val graphQlVersion = "1.0.0"
    const val server = "io.ktor:ktor-server-netty:$version"
    const val tests = "io.ktor:ktor-server-tests:$version"
    const val jwt = "io.ktor:ktor-auth-jwt:$version"
    const val serialization = "io.ktor:ktor-serialization:$version"
    const val graphQl = "com.github.excitement-engineer:ktor-graphql:$graphQlVersion"
}

/**
 * SpringBootのライブラリをまとめているobject
 *
 * @property version SpringBootのバージョン
 * @property crypto 暗号化ライブラリ
 */
object Spring {
    const val version = "5.2.1.RELEASE"
    const val crypto = "org.springframework.security:spring-security-crypto:$version"
}

/**
 * kotlinx.*以下のライブラリをまとめているobject
 *
 * @property version kotlinxのバージョン
 * @property serialization Jsonシリアライズライブラリ
 */
object KotlinX {
    const val version = "0.14.0"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$version"
}

/**
 * データベースのライブラリをまとめているobject
 *
 * @property exposedVersion Exposedのバージョン
 * @property hikariCpVersion HikariCPのバージョン
 * @property mySqlVersion MySqlのConnector/Jのバージョン
 * @property exposed DAO用ライブラリ
 * @property hikariCp コネクションプーリング用ライブラリ
 * @property mySql MySQLのJDBC
 */
object Sql {
    const val exposedVersion = "0.17.7"
    const val hikariCpVersion = "3.4.2"
    const val mySqlVersion = "8.0.18"

    const val exposed = "org.jetbrains.exposed:exposed:$exposedVersion"
    const val hikariCp = "com.zaxxer:HikariCP:$hikariCpVersion"
    const val mySql = "mysql:mysql-connector-java:$mySqlVersion"
}

/**
 * 設定ファイル関係のライブラリをまとめているobject
 *
 * @property version snakeyamlのバージョン
 * @property snakeYaml Yamlパーサライブラリ
 */
object Config {
    const val version = "1.25"
    const val snakeYaml = "org.yaml:snakeyaml:$version"
}