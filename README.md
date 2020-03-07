# MeetLog
Backend Server

# Requirements
* MySQL
* JDK8 or above

# How to run
Create `dbConfig.yml` in project directory(same directory with [build.gradle.kts](build.gradle.kts)).

ex.
```yaml
user: root
password: root
host: localhost
port: 3306
dataBaseName: meetlog
```

Create `core.graphql` in project directory(same directory with [build.gradle.kts](build.gradle.kts)) from [here](https://github.com/MeetLog/meet-log-share/blob/dev/core.graphql).

Run command `./gradlew run`

The server run for port `8080`.