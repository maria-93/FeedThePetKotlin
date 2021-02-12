package ru.kesva.feedthepet.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kesva.feedthepet.domain.model.Pet

@Database(entities = [Pet::class], version = 1, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDataDao(): PetDao
}