package ru.kesva.feedthepet

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import java.util.*

fun hideKeyBoard(activity: Activity) {
    if (activity.window != null) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.window.decorView.windowToken, 0
        )
    } else {
        throw UnsupportedOperationException("Can't show keyboard. Activity's window is null")
    }
}

fun daysToMs(days: Int): Long {
    return hoursToMs(days * 24)
}

fun hoursToMs(hours: Int): Long {
    return minutesToMs(hours * 60)
}

fun minutesToMs(minutes: Int): Long {
    return secondsToMs(minutes * 60)
}

fun secondsToMs(seconds: Int): Long {
    return (seconds * 1000).toLong()
}

fun msToDays(milliseconds: Long): Int {
    return (milliseconds / (1000 * 60 * 60 * 24)).toInt()
}

fun msToHours(milliseconds: Long): Int {
    return ((milliseconds / (1000 * 60 * 60)) % 24).toInt()
}

fun msToMinutes(milliseconds: Long): Int {
    return ((milliseconds / (1000 * 60)) % 60).toInt()
}

fun getFormattedTime(timeInterval: Long): String {
    val minutes = ((timeInterval / (1000 * 60)) % 60).toInt()
    val hours = ((timeInterval / (1000 * 60 * 60)) % 24).toInt()
    val days = (timeInterval / (1000 * 60 * 60 * 24)).toInt()

    when {
        timeInterval < 86400000 -> {
            return String.format(
                Locale.getDefault(),
                "%02dч.%02dмин.",
                hours,
                minutes
            )
        }
        timeInterval < 3600000 -> {
            return String.format(
                Locale.getDefault(),
                "%02dмин.",
                minutes
            )
        }
        else -> {
            return String.format(
                Locale.getDefault(),
                "%02dд.%02dч.%02dмин.",
                days,
                hours,
                minutes
            )
        }
    }
}
