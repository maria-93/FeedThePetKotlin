package ru.kesva.feedthepet.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.MyCountDownTimer
import ru.kesva.feedthepet.data.repository.*
import ru.kesva.feedthepet.di.subcomponents.NotificationServiceComponent
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.usecases.MakePetFedUseCase
import ru.kesva.feedthepet.getRemainTime
import javax.inject.Inject

class NotificationService : IntentService("notification_service") {
    private lateinit var myCountDownTimer: MyCountDownTimer
    private lateinit var component: NotificationServiceComponent

    @Inject
    lateinit var makePetFedUseCase: MakePetFedUseCase

    override fun onCreate() {
        super.onCreate()
        injectDependency(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent!!.action == PET_FED_INTENT_ACTION) {
            Log.d("AlertReceiver", "onHandleIntent: ${intent.action.toString()} ")
            val actionBundle = intent.getBundleExtra(ACTION_BUNDLE) as Bundle
            val petId = actionBundle.getInt(PET_ID)
            val petName = actionBundle.getString(PET_NAME).toString()
            val petTimeInterval = actionBundle.getLong(PET_TIME_INTERVAL)
            val petImageUri = actionBundle.getString(PET_IMAGE_URI).toString()
            val petTimeInFuture = actionBundle.getLong(PET_FUTURE_TIME)
            val pet = Pet(petId, petName, petTimeInterval, petImageUri, petTimeInFuture)
            GlobalScope.launch(Dispatchers.IO) {
                makePetFedUseCase(pet)
            }
            myCountDownTimer = MyCountDownTimer()
            myCountDownTimer.start(getRemainTime(pet.timeInFuture))

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }
    }



    private fun injectDependency(context: Context) {
        component =
            (context.applicationContext as FeedThePetApplication).appComponent.notificationServiceComponent()
                .create()
        component.provideDependenciesFor(this)
    }
}