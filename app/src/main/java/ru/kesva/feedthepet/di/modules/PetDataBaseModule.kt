package ru.kesva.feedthepet.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.data.source.local.PetDatabase
import javax.inject.Singleton

@Module
class PetDatabaseModule {
    private val databaseName = "PetDataBase.db"

    @Singleton
    @Provides
    fun providePetDatabase(@ApplicationContext context: Context): PetDatabase =
        Room.databaseBuilder(context, PetDatabase::class.java, databaseName).build()

}