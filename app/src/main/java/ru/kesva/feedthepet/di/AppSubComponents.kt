package ru.kesva.feedthepet.di

import dagger.Module
import ru.kesva.feedthepet.di.subcomponents.AddNewPetComponent
import ru.kesva.feedthepet.di.subcomponents.StartComponent

@Module(subcomponents = [StartComponent::class, AddNewPetComponent::class])
class AppSubComponents