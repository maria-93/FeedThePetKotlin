package ru.kesva.feedthepet.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.data.repository.*
import ru.kesva.feedthepet.di.subcomponents.AlertReceiverComponent
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.usecases.SendNotificationUseCase
import javax.inject.Inject

class AlertReceiver : BroadcastReceiver() {
    companion object {
        const val FEED_THE_PET_ACTION = "ru.kesva.feedthepet.FEED_PET"
    }

    private lateinit var component: AlertReceiverComponent
    private val TAG = "AlertReceiver"

    @Inject
    lateinit var sendNotificationUseCase: SendNotificationUseCase

    override fun onReceive(context: Context, intent: Intent) {
        injectDependency(context)
        if (intent.action == FEED_THE_PET_ACTION) {
            onFeedPetBroadcastReceive(intent)
        }
    }

    private fun onFeedPetBroadcastReceive(intent: Intent) {
        val bundle = intent.extras!!.getBundle(BUNDLE_KEY) as Bundle
        val petId = bundle.getInt(PET_ID)
        val petName = bundle.getString(PET_NAME).toString()
        val petTimeInterval = bundle.getLong(PET_TIME_INTERVAL)
        val petImageUri = bundle.getString(PET_IMAGE_URI).toString()
        val petTimeInFuture = bundle.getLong(PET_FUTURE_TIME)
        val pet = Pet(petId, petName, petTimeInterval, petImageUri, petTimeInFuture)

        sendNotificationUseCase.sendNotification(pet)
        Log.d(TAG, "onFeedPetBroadcastReceive: ${pet.id}")
    }
    private fun injectDependency(context: Context) {
        component =
            (context.applicationContext as FeedThePetApplication).appComponent.alertReceiverComponent()
                .create()
        component.provideDependenciesFor(this)
    }

}