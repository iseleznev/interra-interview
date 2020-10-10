package me.seleznev.interview.interra

fun link(user: String, email:String, userLinks: MutableMap<String, String>, emails: Map<String, String>) {
    val userActualLink = resolveUser(userLinks, user)
    val emailUserLink = resolveUser(userLinks, emails.getValue(email))
    if (userActualLink != user && userActualLink != emailUserLink) {
        userLinks[emailUserLink] = userActualLink
    }
    if (userActualLink == user && emailUserLink != user) {
        userLinks[user] = emailUserLink
    }
}