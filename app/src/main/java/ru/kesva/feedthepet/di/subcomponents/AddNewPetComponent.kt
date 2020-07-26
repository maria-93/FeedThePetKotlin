package ru.kesva.feedthepet.di.subcomponents

import dagger.Subcomponent
import ru.kesva.feedthepet.di.modules.ClickHandlersProvideModule
import ru.kesva.feedthepet.ui.addnewpetfragment.AddNewPetFragment

@Subcomponent(modules = [ClickHandlersProvideModule::class])
interface AddNewPetComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(clickHandlersProvideModule: ClickHandlersProvideModule): AddNewPetComponent
    }

    fun provideDependenciesFor(addNewPetFragment: AddNewPetFragment)
}