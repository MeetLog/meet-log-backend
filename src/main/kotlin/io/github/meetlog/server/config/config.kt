package io.github.meetlog.server.config

import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths

val yaml = Yaml()
inline fun <reified T> loadConfig(fileName: String): T = yaml.loadAs(Files.newBufferedReader(Paths.get(fileName)), T::class.java)