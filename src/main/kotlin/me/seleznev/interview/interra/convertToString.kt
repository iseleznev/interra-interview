package me.seleznev.interview.interra

fun convertToString(users: Map<String, List<String>>): String {
    return users.toList().joinToString(
        separator = "\n",
        transform = {
            it.first + " -> " + it.second.joinToString(", ")
        }
    )
}