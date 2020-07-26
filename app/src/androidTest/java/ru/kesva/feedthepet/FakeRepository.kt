package ru.kesva.feedthepet

import androidx.lifecycle.LiveData
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository

class FakeRepository : FakeDatabase(),
    Repository {
    override suspend fun update(petData: PetData) {
        pets
    }

    override suspend fun insert(petData: PetData) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(petData: PetData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPetData() {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllPetData(): LiveData<List<PetData>> {
        TODO("Not yet implemented")
    }

}