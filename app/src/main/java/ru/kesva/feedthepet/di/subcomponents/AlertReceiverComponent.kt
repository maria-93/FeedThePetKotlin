package ru.kesva.feedthepet.di.subcomponents

import dagger.Subcomponent
import ru.kesva.feedthepet.service.AlertReceiver

@Subcomponent
interface AlertReceiverComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AlertReceiverComponent
    }
    fun provideDependenciesFor(alertReceiver: AlertReceiver)
}