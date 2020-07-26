package ru.kesva.feedthepet.di.subcomponents

import dagger.Subcomponent
import ru.kesva.feedthepet.di.modules.ClickHandlersProvideModule
import ru.kesva.feedthepet.ui.startfragment.StartFragment

@Subcomponent(modules = [ClickHandlersProvideModule::class])
interface StartComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(clickHandlersProvideModule: ClickHandlersProvideModule): StartComponent
    }

    fun provideDependenciesFor(startFragment: StartFragment)
}