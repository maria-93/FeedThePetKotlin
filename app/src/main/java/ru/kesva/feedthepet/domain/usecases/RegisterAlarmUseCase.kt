package ru.kesva.feedthepet.domain.usecases

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.getPendingIntentForNotification
import java.util.*
import javax.inject.Inject


class RegisterAlarmUseCase @Inject constructor(@ApplicationContext private val context: Context) {

    fun registerAlarm(
        petId: Int,
        triggerTime: Calendar
    ) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
            getPendingIntentForNotification(
                context,
                petId.toLong()
            )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, pendingIntent)
        }
        else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, pendingIntent)
        }

    }
}
