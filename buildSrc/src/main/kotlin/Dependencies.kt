@file:Suppress("MemberVisibilityCanBePrivate")

object Ver {
    const val kotlin = "1.3.61"
}

object Ktor {
    const val version = "1.3.0-rc2"
    const val graphQlVersion = "1.0.0"
    const val server = "io.ktor:ktor-server-netty:$version"
    const val tests = "io.ktor:ktor-server-tests:$version"
    const val auth = "io.ktor:ktor-auth:$version"
    const val jwt = "io.ktor:ktor-auth-jwt:$version"
    const val graphQl = "com.github.excitement-engineer:ktor-graphql:$graphQlVersion"
}

object Spring {
    const val version = "5.2.1.RELEASE"
    const val crypto = "org.springframework.security:spring-security-crypto:$version"
}

object KotlinX {
    const val version = "0.14.0"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$version"
}

object Sql {
    const val exposedVersion = "0.17.7"
    const val hikariCpVersion = "3.4.2"
    const val mySqlVersion = "8.0.18"

    const val exposed = "org.jetbrains.exposed:exposed:$exposedVersion"
    const val hikariCp = "com.zaxxer:HikariCP:$hikariCpVersion"
    const val mySql = "mysql:mysql-connector-java:$mySqlVersion"
}

object Config {
    const val version = "1.25"
    const val snakeYaml = "org.yaml:snakeyaml:$version"
}