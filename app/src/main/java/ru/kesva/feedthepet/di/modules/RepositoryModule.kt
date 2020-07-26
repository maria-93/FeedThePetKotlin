package ru.kesva.feedthepet.di.modules

import dagger.Binds
import dagger.Module
import ru.kesva.feedthepet.domain.repository.Repository
import ru.kesva.feedthepet.data.repository.RepositoryImpl

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository

}