package ru.kesva.feedthepet.domain.repository

import androidx.lifecycle.LiveData
import ru.kesva.feedthepet.domain.model.PetData

interface Repository {
    suspend fun update(petData: PetData)

    suspend fun insert(petData: PetData)

    suspend fun delete(petData: PetData)

    suspend fun deleteAllPetData()

    suspend fun getById(id: Int)

    fun getAllPetData(): LiveData<List<PetData>>
}