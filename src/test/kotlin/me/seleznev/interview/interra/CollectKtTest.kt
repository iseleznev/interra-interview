package me.seleznev.interview.interra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class CollectKtTest {

    @Test
    fun collectTestOnEmailCorrespondToUserDirectly() {
        val expectedUser = UUID.randomUUID().toString()
        val expectedEmail = UUID.randomUUID().toString()
        val parseResult = ParseResult(
            hashMapOf(
                Pair(expectedEmail, expectedUser)
            ),
            hashMapOf(
                Pair(expectedUser, expectedUser)
            )
        )
        val actual = collect(parseResult)

        assertThat(actual).containsKey(expectedUser)
        assertThat(actual.getValue(expectedUser)).containsOnly(expectedEmail)
    }

    @Test
    fun collectTestOnEmailCorrespondPriorityWithDirectlyAndIndirectlyMatchedUsers() {
        val expectedDirectlyMatchedUser = UUID.randomUUID().toString()
        val expectedIndirectlyMatchedUser = UUID.randomUUID().toString()
        val expectedEmail = UUID.randomUUID().toString()
        val parseResult = ParseResult(
            hashMapOf(
                Pair(expectedEmail, expectedIndirectlyMatchedUser)
            ),
            hashMapOf(
                Pair(expectedIndirectlyMatchedUser, expectedDirectlyMatchedUser),
                Pair(expectedDirectlyMatchedUser, expectedDirectlyMatchedUser)
            )
        )
        val actual = collect(parseResult)

        assertThat(actual).containsOnlyKeys(expectedDirectlyMatchedUser)
        assertThat(actual.getValue(expectedDirectlyMatchedUser)).contains(expectedEmail)
    }

    @Test
    fun collectTestOnEmailCorrespondToUserIndirectly() {
        val expectedDirectlyUser = UUID.randomUUID().toString();
        val expectedIndirectlyUser = UUID.randomUUID().toString();
        val expectedEmail = UUID.randomUUID().toString();
        val parseResult = ParseResult(
            hashMapOf(
                Pair(expectedEmail, expectedIndirectlyUser)
            ),
            hashMapOf(
                Pair(expectedIndirectlyUser, expectedDirectlyUser)
            )
        )
        val actual = collect(parseResult)

        assertThat(actual).containsOnlyKeys(expectedDirectlyUser)
        assertThat(actual.getValue(expectedDirectlyUser)).containsOnly(expectedEmail)
    }
}