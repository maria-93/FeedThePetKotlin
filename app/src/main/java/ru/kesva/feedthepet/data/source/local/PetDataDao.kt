package ru.kesva.feedthepet.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.kesva.feedthepet.domain.model.PetData

@Dao
interface PetDataDao {
    /**
     * Изменить существующую запись о животном в БД
     */

    @Update
    suspend fun update(petData: PetData)

    /**
     * Внести запись о новом животном в БД
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(petData: PetData)

    /**
     * Удалить запись об определенном животном из БД
     */

    @Delete
    suspend fun delete(petData: PetData)

    /**
     * Удалить записи обо всех животных из БД
     */
    @Query("DELETE FROM petData")
    suspend fun deleteAllPetData()

    /**
     * Выбрать животное из БД по id
     */
    @Query("SELECT * FROM petData WHERE id = :id")
    suspend fun getById(id: Int): PetData

    /**
     * Возвращает список всех животных, обернутый в LiveData
     */
    @Query("SELECT * FROM petData")
    fun getAllPetData(): LiveData<List<PetData>>


}