package me.seleznev.interview.interra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.StringBuilder
import java.util.*
import kotlin.random.Random

class InterviewInterraTestIT {

    @MethodSource("generate")
    @ParameterizedTest
    fun emailsOptimizeTest(input: String, expected: Map<String, List<String>>) {
        val actual = collect(
            parse(input)
        )
        expected.forEach {
            assertThat(actual).containsKey(it.key)
            assertThat(actual.getValue(it.key)).containsAll(it.value)
        }
        assertThat(actual).hasSameSizeAs(expected)
    }

    companion object {
        @JvmStatic
        fun generate(): List<Arguments> {
            val count = Random.nextInt(10) + 5
            val arguments = mutableListOf<Arguments>()
            repeat(count) {
                val expected = generateUsers()
                val input = usersToString(
                    scatteredUsers(expected)
                )
                arguments.add(
                    Arguments.of(input, expected)
                )
            }
            return arguments
        }

        private fun generateUsers(): Map<String, List<String>> {
            val result = mutableMapOf<String, MutableList<String>>()
            val emailCount = Random.nextInt(100) + 10
            val emails = (1..emailCount).map { UUID.randomUUID().toString() }.toSet()
            var currentUser = UUID.randomUUID().toString()
            result[currentUser] = mutableListOf()
            emails.forEach {
                if (Random.nextInt(4) == 0 && result.getValue(currentUser).size > 0) {
                    currentUser = UUID.randomUUID().toString()
                    result[currentUser] = mutableListOf()
                }
                result.getValue(currentUser).add(it)
            }
            return result
        }

        private fun usersToString(users: Map<String, List<String>>): String {
            val stringBuilder = StringBuilder()
            users.forEach {
                if (stringBuilder.isNotEmpty()) {
                    stringBuilder.appendLine()
                }
                stringBuilder
                    .append(it.key)
                    .append(" -> ")
                    .append(
                        it.value.joinToString(", ")
                    )
            }

            return stringBuilder.toString()
        }

        private fun scatteredUsers(users: Map<String, List<String>>): Map<String, List<String>> {
            val result = mutableMapOf<String, MutableList<String>>()

            users.forEach { entry ->
                result.put(
                    entry.key,
                    mutableListOf()
                )
                val count = if (entry.value.size == 1) 0 else Random.nextInt(entry.value.size - 1)
                repeat(entry.value.size - count) { index ->
                    result.getValue(entry.key).add(
                        entry.value[index]
                    )
                }
                repeat(count) { index ->
                    result.getValue(entry.key).add(
                        entry.value[entry.value.size - count + index]
                    )
                }
            }
            return result
        }
    }
}