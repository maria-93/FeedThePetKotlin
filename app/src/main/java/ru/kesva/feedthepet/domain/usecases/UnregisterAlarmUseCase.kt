package ru.kesva.feedthepet.domain.usecases

import android.app.AlarmManager
import android.content.Context
import android.widget.Toast
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.getPendingIntentForCancel
import javax.inject.Inject


class UnregisterAlarmUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    fun unregisterAlarm(petId: Int) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntentForCancel(context, petId)
        alarmManager.cancel(pendingIntent)
        cancelAlarmShowToast()
    }

    private fun cancelAlarmShowToast() {
        Toast.makeText(context, "Будильник остановлен", Toast.LENGTH_SHORT).show()
    }
}