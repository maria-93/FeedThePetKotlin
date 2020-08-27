package ru.kesva.feedthepet.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kesva.feedthepet.data.source.local.PetDatabase
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    petDatabase: PetDatabase
) : Repository {

    private val petDataDao = petDatabase.petDataDao()
    private lateinit var pet: Pet
    override suspend fun update(pet: Pet) {
        withContext(Dispatchers.IO) {
            petDataDao.update(pet)
        }
    }

    override suspend fun insert(pet: Pet) {
        withContext(Dispatchers.IO) {
            petDataDao.insert(pet)
        }
    }

    override suspend fun delete(pet: Pet) {
        withContext(Dispatchers.IO) {
            petDataDao.delete(pet)
        }
    }

    override suspend fun deleteAllPetData() {
        withContext(Dispatchers.IO) {
            petDataDao.deleteAllPetData()
        }
    }

    override suspend fun getById(id: Int): Pet {
        withContext(Dispatchers.IO) {
           pet = petDataDao.getById(id)
        }
        return pet
    }

    override fun getAllPetData(): LiveData<List<Pet>> {
        return petDataDao.getAllPetData()
    }
}