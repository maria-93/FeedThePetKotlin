package ru.kesva.feedthepet.di.modules

import dagger.Binds
import dagger.Module
import ru.kesva.feedthepet.data.repository.AlarmRepositoryImpl
import ru.kesva.feedthepet.domain.repository.AlarmRepository

@Module
abstract class AlarmRepositoryModule {
    @Binds
    abstract fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository
}