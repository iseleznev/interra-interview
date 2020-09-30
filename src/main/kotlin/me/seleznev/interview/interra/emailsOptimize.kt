package me.seleznev.interview.interra

fun emailsOptimize(content: String): String {
    return convertToString(
        collect(
            parse(content)
        )
    )
}