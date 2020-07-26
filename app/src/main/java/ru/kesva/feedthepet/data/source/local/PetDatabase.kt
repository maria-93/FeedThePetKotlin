package ru.kesva.feedthepet.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kesva.feedthepet.domain.model.PetData

@Database(entities = [PetData::class], version = 1, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDataDao(): PetDataDao
}