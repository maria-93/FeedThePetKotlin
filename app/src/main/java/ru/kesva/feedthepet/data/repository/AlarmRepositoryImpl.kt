package ru.kesva.feedthepet.data.repository

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import ru.kesva.feedthepet.service.AlertReceiver
import ru.kesva.feedthepet.service.NotificationService
import ru.kesva.feedthepet.ui.MainActivity
import javax.inject.Inject

const val NOTIFICATION_CHANNEL_ID = "notification_channel"
const val STARTING_ACTIVITY_PENDING_INTENT_ID = 100
const val PET_FED_INTENT_ACTION = "pet_fed"
const val ACTION_PENDING_INTENT_ID = 200

const val PET_NAME = "pet_name"
const val PET_ID = "pet_id"
const val PET_TIME_INTERVAL = "pet_time_interval"
const val PET_FUTURE_TIME = "pet_time_in_future"
const val PET_IMAGE_URI = "pet_image_uri"
const val BUNDLE_KEY = "bundle_key"
const val ACTION_BUNDLE = "action_bundle"

class AlarmRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    AlarmRepository {

    override fun startAlarm(
        pet: Pet,
        triggerTime: Long
    ) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
            getPendingIntentForNotification(
                context,
                pet
            )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            Log.d("Test!", "alarmRepository: start alarm for pet ${pet.petName}")
        } else {
            Log.d("Test!", "alarmRepository: start alarm for pet ${pet.petName}")
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent
            )
        }
    }



    override fun cancelAlarmFor(pet: Pet) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntentForCancel(context, pet)
        alarmManager.cancel(pendingIntent)
    }

    private fun getPendingIntentForNotification(
        context: Context,
        pet: Pet
    ): PendingIntent {
        val bundle = Bundle().apply {
            putInt(PET_ID, pet.id)
            putString(PET_NAME, pet.petName)
            putLong(PET_TIME_INTERVAL, pet.timeInterval)
            putString(PET_IMAGE_URI, pet.petImageURI)
            putLong(PET_FUTURE_TIME, pet.timeInFuture)
        }
        val intent = Intent(context, AlertReceiver::class.java).apply {
            action = AlertReceiver.FEED_THE_PET_ACTION
            putExtra(BUNDLE_KEY, bundle)
        }
        Log.d("AlertReceiver", "putExtra: ${pet.id}")
        return PendingIntent.getBroadcast(
            context,
            pet.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getPendingIntentForCancel(
        context: Context,
        pet: Pet
    ): PendingIntent {
        val intent = Intent(context, AlertReceiver::class.java)
        intent.action = AlertReceiver.FEED_THE_PET_ACTION
        return PendingIntent.getBroadcast(
            context,
            pet.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun sendNotification(pet: Pet) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, "Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setSmallIcon(R.drawable.ic_action_name)
            .setContentTitle(context.getString(R.string.notification_title, pet.petName))
            .setContentText(context.getString(R.string.notification_body))
            .setStyle(NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_body)))
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(openMainActivity(context))
            .addAction(
                R.drawable.ic_action_name,
                context.getString(R.string.pet_fed),
                actionIntent(context, pet)
            )
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.priority = NotificationCompat.PRIORITY_HIGH
        }
        notificationManager.notify(pet.id, builder.build())
    }

    private fun openMainActivity(context: Context): PendingIntent {
        val startActivityIntent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            STARTING_ACTIVITY_PENDING_INTENT_ID,
            startActivityIntent,
            0
        )
    }

    private fun actionIntent(context: Context, pet: Pet): PendingIntent {
        val actionBundle = Bundle().apply {
            putInt(PET_ID, pet.id)
            putString(PET_NAME, pet.petName)
            putLong(PET_TIME_INTERVAL, pet.timeInterval)
            putString(PET_IMAGE_URI, pet.petImageURI)
            putLong(PET_FUTURE_TIME, pet.timeInFuture)
        }
        Log.d(
            "AlertReceiver",
            "actionIntent in sendNotification: ${pet.id} ${pet.petName} ${pet.timeInterval}"
        )
        val actionIntent = Intent(context, NotificationService::class.java).apply {
            action = PET_FED_INTENT_ACTION
            putExtra(ACTION_BUNDLE, actionBundle)
        }

        return PendingIntent.getService(
            context,
            ACTION_PENDING_INTENT_ID,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}