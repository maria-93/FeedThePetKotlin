package ru.kesva.feedthepet.domain.repository

import androidx.lifecycle.LiveData
import ru.kesva.feedthepet.domain.model.Pet

interface Repository {
    suspend fun update(pet: Pet)

    suspend fun insert(pet: Pet)

    suspend fun delete(pet: Pet)

    suspend fun deleteAllPetData()

    suspend fun getById(id: Int): Pet

    fun getAllPetData(): LiveData<List<Pet>>
}