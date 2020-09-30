package me.seleznev.interview.interra

fun resolveUser(userLinks: Map<String, String>, user: String): String {
    var userFound = user
    while (userLinks.containsKey(userFound)) {
        userFound = userLinks.getValue(userFound)
    }
    return userFound;
}