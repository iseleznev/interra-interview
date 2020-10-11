package me.seleznev.interview.interra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class LinkKtTest {

    @Test
    fun linkTestEmailLinkedToUserAndTestUserDoesNotLinkedAndEmailUserLinkedToOther() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val otherUser = UUID.randomUUID().toString()
        val emailUser = UUID.randomUUID().toString()
        val userLinks = hashMapOf(
            Pair(emailUser, otherUser)
        )
        val emails = hashMapOf(
            Pair(email, emailUser)
        )

        link(
            user,
            email,
            userLinks,
            emails
        )
        assertThat(userLinks).containsOnlyKeys(user, emailUser)
        assertThat(userLinks.getValue(user)).isEqualTo(otherUser)
        assertThat(userLinks.getValue(emailUser)).isEqualTo(otherUser)
    }

    @Test
    fun linkTestEmailLinkedToUserAndTestUserAlreadyLinked() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val otherUser = UUID.randomUUID().toString()
        val emailUser = UUID.randomUUID().toString()
        val userLinks = hashMapOf(
            Pair(user, otherUser)
        )
        val emails = hashMapOf(
            Pair(email, emailUser)
        )

        link(
            user,
            email,
            userLinks,
            emails
        )
        assertThat(userLinks).containsOnlyKeys(user, emailUser)
        assertThat(userLinks.getValue(user)).isEqualTo(otherUser)
        assertThat(userLinks.getValue(emailUser)).isEqualTo(otherUser)
    }

    @Test
    fun linkTestEmailLinkedToUserAndTestUserDoesNotLinked() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val emailUser = UUID.randomUUID().toString()
        val userLinks = hashMapOf<String, String>()
        val emails = hashMapOf(
            Pair(email, emailUser)
        )

        link(
            user,
            email,
            userLinks,
            emails
        )
        assertThat(userLinks).containsOnlyKeys(user)
        assertThat(userLinks.getValue(user)).isEqualTo(emailUser)
    }

    @Test
    fun linkTestEmailHasNoOtherUser() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val userLinks = hashMapOf<String, String>()
        val emails = hashMapOf(
            Pair(email, user)
        )

        link(
            user,
            email,
            userLinks,
            emails
        )
        assertThat(userLinks).doesNotContainKey(user)
    }
}