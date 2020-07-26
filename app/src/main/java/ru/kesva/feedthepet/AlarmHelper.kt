package ru.kesva.feedthepet

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.kesva.feedthepet.service.AlertReceiver
import ru.kesva.feedthepet.ui.startfragment.StartFragment

const val NOTIFICATION_CHANNEL_ID = "notification_channel"
const val STARTING_ACTIVITY_PENDING_INTENT_ID = 100

fun getPendingIntentForNotification(
    context: Context,
    petId: Long
): PendingIntent {
    val intent = Intent(context, AlertReceiver::class.java)
    intent.putExtra("pet_id_key", petId)
    return PendingIntent.getBroadcast(
        context,
        petId.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}

fun getPendingIntentForCancel(
    context: Context,
    petId: Int
): PendingIntent {
    val intent = Intent(context, AlertReceiver::class.java)
    return PendingIntent.getBroadcast(
        context,
        petId,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}

fun sendNotification(context: Context, petId: Long) {
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
        .setContentTitle("Покорми питомца!")
        .setContentText("Настало время покормить питомца")
        .setStyle(NotificationCompat.BigTextStyle().bigText("Настало время покормить питомца"))
        .setDefaults(Notification.DEFAULT_VIBRATE)
        .setContentIntent(launchApp(context))
        .setAutoCancel(true)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        builder.priority = NotificationCompat.PRIORITY_HIGH
    }
    notificationManager.notify(petId.toInt(), builder.build())
}

private fun launchApp(context: Context): PendingIntent {
    val startActivityIntent = Intent(context, StartFragment::class.java)
    return PendingIntent.getActivity(
        context,
        STARTING_ACTIVITY_PENDING_INTENT_ID,
        startActivityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}
