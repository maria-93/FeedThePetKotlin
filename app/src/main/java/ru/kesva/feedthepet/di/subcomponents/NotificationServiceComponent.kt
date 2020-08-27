package ru.kesva.feedthepet.di.subcomponents

import dagger.Subcomponent
import ru.kesva.feedthepet.service.NotificationService

@Subcomponent
interface NotificationServiceComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): NotificationServiceComponent
    }

    fun provideDependenciesFor(notificationService: NotificationService)
}