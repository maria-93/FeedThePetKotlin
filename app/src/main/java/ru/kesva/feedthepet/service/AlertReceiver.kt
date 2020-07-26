package ru.kesva.feedthepet.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.kesva.feedthepet.sendNotification
import javax.inject.Inject

class AlertReceiver @Inject constructor() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val petId: Long = intent.getLongExtra("pet_id", -1)
        sendNotification(context, petId)
    }

}