package me.seleznev.interview.interra

class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val content = StringBuilder()
            var input = readLine()
            while (input != null && !input.isBlank()) {
                content.appendLine(input)
                input = readLine()
            }
            println(
                emailsOptimize(
                    content.toString()
                )
            )
        }
    }
}