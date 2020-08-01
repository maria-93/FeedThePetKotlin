package ru.kesva.feedthepet

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsKtTest {

    @Test
    fun msToDaysTest() {
        val milliseconds: Long = 172800000

        val result = msToDays(milliseconds)

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun msToHoursTest() {
        val milliseconds: Long = 7200000

        val result = msToHours(milliseconds)

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun msToMinutesTest() {
        val milliseconds: Long = 120000

        val result = msToMinutes(milliseconds)

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun daysToMsTest() {
        val days: Int = 2

        val result = daysToMs(days)

        assertThat(result).isEqualTo(172800000)
    }

    @Test
    fun minutesToMsTest() {
        val minutes = 5

        val result = minutesToMs(minutes)

        assertThat(result).isEqualTo(300000)
    }

    @Test
    fun secondsToMsTest() {
        val seconds = 5

        val result = secondsToMs(seconds)

        assertThat(result).isEqualTo(5000)
    }
}