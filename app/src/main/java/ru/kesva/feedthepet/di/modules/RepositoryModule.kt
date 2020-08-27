package ru.kesva.feedthepet.di.modules

import dagger.Binds
import dagger.Module
import ru.kesva.feedthepet.data.repository.RepositoryImpl
import ru.kesva.feedthepet.domain.repository.Repository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

}