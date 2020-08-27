package ru.kesva.feedthepet.di

import dagger.Module
import ru.kesva.feedthepet.di.subcomponents.*

@Module(
    subcomponents = [StartComponent::class, AddNewPetComponent::class,
        MainActivityComponent::class, AlertReceiverComponent::class, NotificationServiceComponent::class]
)
class AppSubComponents