package io.github.meetlog.server.config

import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths

private val yaml = Yaml()
fun <T> loadConfig(fileName: String): T = yaml.load<T>(Files.newBufferedReader(Paths.get(fileName)))