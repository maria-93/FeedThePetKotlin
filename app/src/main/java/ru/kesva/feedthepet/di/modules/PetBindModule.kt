package ru.kesva.feedthepet.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kesva.feedthepet.ui.viewmodel.PetViewModel
import ru.kesva.feedthepet.di.ViewModelKey

@Module
abstract class PetBindModule {
    @Binds
    @IntoMap
    @ViewModelKey(PetViewModel::class)
    abstract fun bindPetDataViewModel(viewModel: PetViewModel): ViewModel
}