package ru.kesva.feedthepet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository

class FakeRepository : Repository {



    val petDataSet = mutableSetOf<PetData>()
    val liveData = MutableLiveData<List<PetData>>()

    override suspend fun update(petData: PetData) {
        if (petDataSet.contains(petData)) {
            petDataSet.add(petData)
            notifyObservers()
        }
    }

    override suspend fun insert(petData: PetData) {
        petDataSet.add(petData)
        notifyObservers()
    }

    override suspend fun delete(petData: PetData) {
        petDataSet.remove(petData)
        notifyObservers()
    }

    override suspend fun deleteAllPetData() {
        petDataSet.clear()
        notifyObservers()
    }

    override suspend fun getById(id: Int): PetData =
        petDataSet.find { it.id == id } ?: throw Exception("PetData not found")


    override fun getAllPetData(): LiveData<List<PetData>> {
        return liveData
    }

    protected fun notifyObservers() {
        liveData.value = getPetDataCopySortedById(petDataSet)


    }

    private fun getPetDataCopySortedById(petDataCollection: Collection<PetData>): MutableList<PetData> {
        val list = mutableListOf<PetData>()
        petDataCollection.forEach { originalPetData ->
            list.add(originalPetData.getCopy())
        }
        list.sortBy { it.id }
        return list
    }

}