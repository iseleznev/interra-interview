package me.seleznev.interview.interra

import java.io.File

fun main(args: Array<String>) {
    println(
        emailsOptimize(
            File(args[0]).readText(Charsets.UTF_8)
        )
    )
}