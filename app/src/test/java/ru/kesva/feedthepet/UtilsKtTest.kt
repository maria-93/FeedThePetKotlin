package ru.kesva.feedthepet

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsKtTest {

    @Test
    fun msToDaysTest() {
        val milliseconds: Long = 6652800000

        val result = msToDays(milliseconds)

        assertThat(result).isEqualTo(77)
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
        val days = 25L

        val result = daysToMs(days)

        assertThat(result).isEqualTo(2160000000)
    }

    @Test
    fun hoursToMsTest() {
        val hours = 23L

        val result = hoursToMs(hours)

        assertThat(result).isEqualTo(82800000)
    }

    @Test
    fun minutesToMsTest() {
        val minutes = 59L

        val result = minutesToMs(minutes)

        assertThat(result).isEqualTo(3540000)
    }

    @Test
    fun secondsToMsTest() {
        val seconds = 59L

        val result = secondsToMs(seconds)

        assertThat(result).isEqualTo(59000)
    }
}