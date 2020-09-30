package me.seleznev.interview.interra

fun parse(content: String): ParseResult {

    val emails = hashMapOf<String, String>()
    val userLinks = hashMapOf<String, String>()

    content.lines().forEach {
        if (it.isBlank()) {
            return@forEach
        }
        val splitResult = it.split("->")
        val user = splitResult[0].trim()
        splitResult[1]
            .split(",")
            .map { email -> email.trim().toLowerCase() }
            .forEach { email ->
                if (user != emails.getOrPut(email, { user })) {
                    userLinks[user] = resolveUser(userLinks, emails.getValue(email))
                }
            }
    }
    return ParseResult(emails, userLinks)
}