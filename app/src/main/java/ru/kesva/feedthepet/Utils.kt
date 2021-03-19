package ru.kesva.feedthepet

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import java.util.*

fun hideKeyBoard(activity: Activity) {
    if (activity.window != null) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.window.decorView.windowToken, 0
        )
        inputMethodManager.showSoftInput(activity.window.decorView, 0)
    } else {
        throw UnsupportedOperationException("Can't show keyboard. Activity's window is null")
    }
}
//провален
fun daysToMs(days: Long): Long {
    return hoursToMs(days * 24)
}

fun hoursToMs(hours: Long): Long {
    return minutesToMs(hours * 60)
}

fun minutesToMs(minutes: Long): Long {
    return secondsToMs(minutes * 60)
}

fun secondsToMs(seconds: Long): Long {
    return (seconds * 1000).toLong()
}

fun msToDays(milliseconds: Long): Int {
    val days = (milliseconds / (1000 * 60 * 60 * 24))
    return days.toInt()
}

fun msToHours(milliseconds: Long): Int {
    val hours = ((milliseconds / (1000 * 60 * 60)) % 24)
    return hours.toInt()
}

fun msToMinutes(milliseconds: Long): Int {
    val minutes = ((milliseconds / (1000 * 60)) % 60)
    return minutes.toInt()
}

fun getFormattedTime(timeInterval: Long): String {
    val minutes = ((timeInterval / (1000 * 60)) % 60)
    val hours = ((timeInterval / (1000 * 60 * 60)) % 24)
    val days = (timeInterval / (1000 * 60 * 60 * 24))
    val locale = Locale.getDefault()

    when {
        timeInterval in 3600000..86399999 -> {
            return if (locale.language == "en" && locale.country == "US") {
                String.format(
                    locale,
                    "%02dh. %02dm.",
                    hours,
                    minutes
                )
            } else {
                String.format(
                    locale,
                    "%02dч. %02dм.",
                    hours,
                    minutes
                )
            }
        }

        timeInterval < 3600000 -> {
            return if (locale.language == "en" && locale.country == "US") {
                return String.format(
                    locale,
                    "%02dm.",
                    minutes
                )
            } else {
                String.format(
                    locale,
                    "%02dм.",
                    minutes
                )
            }
        }

        else -> {
            return if (locale.language == "en" && locale.country == "US") {
                String.format(
                    locale,
                    "%02dd. %02dh. %02dm.",
                    days,
                    hours,
                    minutes
                )
            } else {
                String.format(
                    locale,
                    "%02dд. %02dч. %02dм.",
                    days,
                    hours,
                    minutes
                )
            }
        }
    }
}

fun getRemainTime(timeInFuture: Long): Long {
    return timeInFuture - System.currentTimeMillis()
}
