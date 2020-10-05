package me.seleznev.interview.interra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class ParseKtTest {

    @Test
    fun parseTestUserEmailLine() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val content = "$user -> $email"
        val actual = parse(content)
        assertThat(actual.userLinks).isEmpty()
        assertThat(actual.emails).containsOnlyKeys(email)
        assertThat(actual.emails.getValue(email)).isEqualTo(user)
    }

    @Test
    fun parseTestUserWithBlankEmailInLine() {
        val user = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val lastEmail = UUID.randomUUID().toString()
        val content = "$user -> $firstEmail, , $lastEmail"
        val actual = parse(content)
        assertThat(actual.userLinks).isEmpty()
        assertThat(actual.emails).containsOnlyKeys(firstEmail, lastEmail, "")
        assertThat(actual.emails.getValue(firstEmail)).isEqualTo(user)
        assertThat(actual.emails.getValue("")).isEqualTo(user)
        assertThat(actual.emails.getValue(lastEmail)).isEqualTo(user)
    }

    @Test
    fun parseTestWithUserWithoutEmails() {
        val user = UUID.randomUUID().toString()
        val content = "$user -> "
        val actual = parse(content)
        assertThat(actual.userLinks).isEmpty()
        assertThat(actual.emails).containsOnlyKeys("")
        assertThat(actual.emails.getValue("")).isEqualTo(user)
    }

    @Test
    fun parseTestWithoutBeforeEmails() {
        val email = UUID.randomUUID().toString()
        val content = "-> $email"
        val actual = parse(content)
        assertThat(actual.userLinks).isEmpty()
        assertThat(actual.emails).containsOnlyKeys(email)
        assertThat(actual.emails.getValue(email)).isEqualTo("")
    }

    @Test
    fun parseTestWithUserWithoutEmailsWithoutDelimeter() {
        val user = UUID.randomUUID().toString()
        val actual = parse(user)
        assertThat(actual.userLinks).containsKey(user)
        assertThat(actual.userLinks.getValue(user)).isEqualTo(user)
        assertThat(actual.emails).isEmpty()
    }

    @Test
    fun parseTestUserWithDifferentEmails() {
        val user = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val secondEmail = UUID.randomUUID().toString()
        val content = "$user -> $firstEmail, $secondEmail"
        val actual = parse(content)
        assertThat(actual.emails).containsOnlyKeys(firstEmail, secondEmail)
        assertThat(actual.emails.getValue(firstEmail)).isEqualTo(user)
        assertThat(actual.emails.getValue(secondEmail)).isEqualTo(user)
    }

    @Test
    fun parseTestUserWithSameEmails() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val content = "$user -> $email, $email"
        val actual = parse(content)
        assertThat(actual.emails).containsKey(email)
        assertThat(actual.emails.getValue(email)).isEqualTo(user)
    }

    @Test
    fun parseTestDifferentUsersWithDifferentEmails() {
        val firstUser = UUID.randomUUID().toString()
        val secondUser = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val secondEmail = UUID.randomUUID().toString()
        val content = "$firstUser -> $firstEmail\n$secondUser -> $secondEmail"
        val actual = parse(content)
        assertTrue(actual.emails.containsKey(firstEmail))
        assertTrue(actual.emails.size == 2)
        assertTrue(actual.emails[firstEmail] == firstUser)
        assertTrue(actual.emails[secondEmail] == secondUser)
    }

    @Test
    fun parseTestDifferentUsersWithSameEmail() {
        val firstUser = UUID.randomUUID().toString()
        val secondUser = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val content = "$firstUser -> $email\n$secondUser -> $email"
        val actual = parse(content)
        assertThat(actual.userLinks).containsOnlyKeys(secondUser)
        assertThat(actual.userLinks.getValue(secondUser)).isEqualTo(firstUser)
        assertThat(actual.emails).containsKey(email)
        assertThat(actual.emails.getValue(email)).isEqualTo(firstUser)
    }

    @Test
    fun parseTestOtherUserCollectedInOneWithSameEmailAfterOtherDifferent() {
        val firstUser = UUID.randomUUID().toString()
        val secondUser = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val secondEmail = UUID.randomUUID().toString()
        val content = "$firstUser -> $firstEmail\n$secondUser -> $secondEmail, $firstEmail"
        val actual = parse(content)

        assertThat(actual.userLinks).containsOnlyKeys(secondUser)
        assertThat(actual.userLinks.getValue(secondUser)).isEqualTo(firstUser)
        assertThat(actual.emails).containsOnlyKeys(firstEmail, secondEmail)
        assertThat(actual.emails.getValue(firstEmail)).isEqualTo(firstUser)
        assertThat(actual.emails.getValue(secondEmail)).isEqualTo(secondUser)
    }

    @Test
    fun parseTestOtherUserCollectedInOneWithSameEmailBeforeOtherDifferent() {
        val firstUser = UUID.randomUUID().toString()
        val secondUser = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val secondEmail = UUID.randomUUID().toString()
        val content = "$firstUser -> $firstEmail\n$secondUser -> $firstEmail, $secondEmail"
        val actual = parse(content)

        assertThat(actual.userLinks).containsOnlyKeys(secondUser)
        assertThat(actual.userLinks.getValue(secondUser)).isEqualTo(firstUser)
        assertThat(actual.emails).containsOnlyKeys(firstEmail, secondEmail)
        assertThat(actual.emails.getValue(firstEmail)).isEqualTo(firstUser)
        assertThat(actual.emails.getValue(secondEmail)).isEqualTo(secondUser)
    }

    @Test
    fun parseTestEmailTrimInLine() {
        val user = UUID.randomUUID().toString()
        val firstEmail = UUID.randomUUID().toString()
        val secondEmail = UUID.randomUUID().toString()
        val content = "$user -> $firstEmail , $secondEmail "
        val actual = parse(content)

        assertThat(actual.emails).containsOnlyKeys(firstEmail, secondEmail)
    }

    @Test
    fun parseTestUserEmailSeparatorTrimInLine() {
        val user = UUID.randomUUID().toString()
        val email = UUID.randomUUID().toString()
        val content = "$user -> $email"
        val actual = parse(content)

        assertThat(actual.emails).containsKey(email)
        assertThat(actual.emails.getValue(email)).isEqualTo(user)
    }
}