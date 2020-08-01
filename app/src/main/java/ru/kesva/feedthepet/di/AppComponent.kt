package ru.kesva.feedthepet.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.di.modules.PetDataBindModule
import ru.kesva.feedthepet.di.modules.PetDatabaseModule
import ru.kesva.feedthepet.di.modules.RepositoryModule
import ru.kesva.feedthepet.di.subcomponents.AddNewPetComponent
import ru.kesva.feedthepet.di.subcomponents.MainActivityComponent
import ru.kesva.feedthepet.di.subcomponents.StartComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubComponents::class, PetDatabaseModule::class,  RepositoryModule::class, PetDataBindModule::class, ViewModelBuilder::class])
interface AppComponent {

    fun provideDependenciesFor(application: FeedThePetApplication)

    fun startComponent(): StartComponent.Factory
    fun addNewPetComponent(): AddNewPetComponent.Factory
    fun mainActivityComponent(): MainActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@ApplicationContext @BindsInstance context: Context): AppComponent
    }


}