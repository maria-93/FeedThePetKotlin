package ru.kesva.feedthepet.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kesva.feedthepet.ui.viewmodel.PetDataViewModel
import ru.kesva.feedthepet.di.ViewModelKey

@Module
abstract class PetDataBindModule {
    @Binds
    @IntoMap
    @ViewModelKey(PetDataViewModel::class)
    abstract fun bindPetDataViewModel(viewModel: PetDataViewModel): ViewModel
}