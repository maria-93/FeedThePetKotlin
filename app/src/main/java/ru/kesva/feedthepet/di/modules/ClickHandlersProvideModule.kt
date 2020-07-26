package ru.kesva.feedthepet.di.modules

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.addnewpetfragment.PetCreationClickHandler
import ru.kesva.feedthepet.ui.startfragment.PetDataAdapter
import ru.kesva.feedthepet.ui.viewmodel.PetDataViewModel

@Module
class ClickHandlersProvideModule(private val fragment: Fragment) {

    @Provides
    fun provideAdapterClickHandler(factory: ViewModelFactory) =
        fragment.getViewModel<PetDataViewModel>(factory) as PetDataAdapter.AdapterClickHandler

    @Provides
    fun providePetCreationClickHandler(factory: ViewModelFactory) =
        fragment.getViewModel<PetDataViewModel>(factory) as PetCreationClickHandler

}