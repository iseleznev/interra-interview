package me.seleznev.interview.interra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class ResolveUserKtTest {

    @Test
    fun resolveUserTestWithUserThatNotLinkedToOther() {
        val user = UUID.randomUUID().toString()
        val linkedUsers = mutableMapOf(
            Pair(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        )
        val actual = resolveUser(linkedUsers, user)

        assertThat(actual).isEqualTo(user)
    }

    @Test
    fun resolveUserTestWithUserThatLinkedToOther() {
        val expected = UUID.randomUUID().toString()
        val userBetweenSpecifiedAndExpected = UUID.randomUUID().toString()
        val userSpecified = UUID.randomUUID().toString()
        val linkedUsers = mutableMapOf(
            Pair(userSpecified, userBetweenSpecifiedAndExpected),
            Pair(userBetweenSpecifiedAndExpected, expected)
        )
        val actual = resolveUser(linkedUsers, userSpecified)

        assertThat(actual).isEqualTo(expected)
    }
}