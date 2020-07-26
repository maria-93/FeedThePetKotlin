package ru.kesva.feedthepet.data.source.local

import androidx.room.TypeConverter
import java.util.*
object CalendarConverter {
    @TypeConverter
    @JvmStatic
    fun toLong(timestamp: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar
    }

    @TypeConverter
    @JvmStatic
    fun toCalendar(calendar: Calendar): Long {
        return calendar.timeInMillis
    }
}