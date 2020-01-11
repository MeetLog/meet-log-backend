@file:Suppress("unused")
import org.gradle.kotlin.dsl.DependencyHandlerScope

object Ver {
    const val hoge = "fuga"
}

val DependencyHandlerScope.hoge: String
    get() = "hoge:fuga:${Ver.hoge}"