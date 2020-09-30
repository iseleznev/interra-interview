package me.seleznev.interview.interra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class ConvertToStringKtTest {

    @Test
    fun convertTestWithUserToEmailJoin() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val users = mapOf(Pair(user, listOf(email)))
        val actual = convertToString(users)

        assertThat(actual).isEqualTo("$user -> $email")
    }

    @Test
    fun convertTestWithUserToFewEmailsJoin() {
        val user = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val secondEmail = UUID.randomUUID().toString()
        val users = mapOf(Pair(user, listOf(firstEmail, secondEmail)))
        val actual = convertToString(users)

        assertThat(actual).isEqualTo("$user -> $firstEmail, $secondEmail")
    }

    @Test
    fun convertTestWithFewUsersJoin() {
        val firstUser = UUID.randomUUID().toString()
        val secondUser = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val users = mapOf(
            Pair(firstUser, listOf(email)),
            Pair(secondUser, listOf(email))
        )
        val actual = convertToString(users)

        assertThat(actual).isEqualTo("$firstUser -> $email\n$secondUser -> $email")
    }
}