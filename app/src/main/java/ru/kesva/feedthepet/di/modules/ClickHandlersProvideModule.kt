package ru.kesva.feedthepet.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.MainActivity
import ru.kesva.feedthepet.ui.addnewpetfragment.PetCreationClickHandler
import ru.kesva.feedthepet.ui.startfragment.PetDataAdapter
import ru.kesva.feedthepet.ui.viewmodel.PetDataViewModel

@Module
class ClickHandlersProvideModule(private val mainActivity: MainActivity) {

    @Provides
    fun provideAdapterClickHandler(factory: ViewModelFactory) =
        mainActivity.getViewModel<PetDataViewModel>(factory) as PetDataAdapter.AdapterClickHandler

    @Provides
    fun providePetCreationClickHandler(factory: ViewModelFactory) =
        mainActivity.getViewModel<PetDataViewModel>(factory) as PetCreationClickHandler

}