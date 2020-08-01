package ru.kesva.feedthepet.di.subcomponents

import dagger.Subcomponent
import ru.kesva.feedthepet.ui.MainActivity

@Subcomponent
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun provideDependenciesFor(mainActivity: MainActivity)
}