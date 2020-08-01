package ru.kesva.feedthepet.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kesva.feedthepet.data.source.local.PetDatabase
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    petDatabase: PetDatabase
) : Repository {

    private val petDataDao = petDatabase.petDataDao()

    override suspend fun update(petData: PetData) {
        withContext(Dispatchers.IO) {
            petDataDao.update(petData)
        }
    }

    override suspend fun insert(petData: PetData) {
        withContext(Dispatchers.IO) {
            petDataDao.insert(petData)
        }
    }

    override suspend fun delete(petData: PetData) {
        withContext(Dispatchers.IO) {
            petDataDao.delete(petData)
        }
    }

    override suspend fun deleteAllPetData() {
        withContext(Dispatchers.IO) {
            petDataDao.deleteAllPetData()
        }
    }

    override suspend fun getById(id: Int): PetData {
        TODO("Not yet implemented")
    }

    override fun getAllPetData(): LiveData<List<PetData>> {
        return petDataDao.getAllPetData()
    }

}