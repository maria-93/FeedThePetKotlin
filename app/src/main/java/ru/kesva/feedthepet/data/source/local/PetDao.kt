package ru.kesva.feedthepet.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.kesva.feedthepet.domain.model.Pet

@Dao
interface PetDao {
    /**
     * Изменить существующую запись о животном в БД
     */

    @Update
    suspend fun update(pet: Pet)

    /**
     * Внести запись о новом животном в БД
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pet: Pet)

    /**
     * Удалить запись об определенном животном из БД
     */

    @Delete
    suspend fun delete(pet: Pet)

    /**
     * Удалить записи обо всех животных из БД
     */
    @Query("DELETE FROM pets")
    suspend fun deleteAllPetData()

    /**
     * Выбрать животное из БД по id
     */
    @Query("SELECT * FROM pets WHERE id = :id")
    suspend fun getById(id: Int): Pet

    /**
     * Возвращает список всех животных, обернутый в LiveData
     */
    @Query("SELECT * FROM pets")
    fun getAllPetData(): LiveData<List<Pet>>


}