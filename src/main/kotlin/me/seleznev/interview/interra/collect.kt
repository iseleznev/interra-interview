package me.seleznev.interview.interra

fun collect(parseResult: ParseResult): Map<String, List<String>> {
    return parseResult.emails.toList()
        .groupBy { pair -> parseResult.userLinks.getOrDefault(pair.second, pair.second) }
        .mapValues { entry -> entry.value.map { it.first } }
}