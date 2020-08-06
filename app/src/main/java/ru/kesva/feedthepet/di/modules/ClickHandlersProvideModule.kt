package ru.kesva.feedthepet.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.MainActivity
import ru.kesva.feedthepet.ui.addnewpetfragment.PetCreationClickHandler
import ru.kesva.feedthepet.ui.startfragment.PetAdapter
import ru.kesva.feedthepet.ui.viewmodel.PetViewModel

@Module
class ClickHandlersProvideModule(private val mainActivity: MainActivity) {

    @Provides
    fun provideAdapterClickHandler(factory: ViewModelFactory) =
        mainActivity.getViewModel<PetViewModel>(factory) as PetAdapter.AdapterClickHandler

    @Provides
    fun providePetCreationClickHandler(factory: ViewModelFactory) =
        mainActivity.getViewModel<PetViewModel>(factory) as PetCreationClickHandler

}