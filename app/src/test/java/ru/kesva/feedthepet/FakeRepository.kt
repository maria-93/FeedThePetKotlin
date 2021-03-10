package ru.kesva.feedthepet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.Repository

class FakeRepository : Repository {

    val petDataSet = mutableSetOf<Pet>()
    val liveData = MutableLiveData<List<Pet>>()

    override suspend fun update(pet: Pet) {
        if (petDataSet.contains(pet)) {
            petDataSet.add(pet)
            notifyObservers()
        }
    }

    override suspend fun insert(pet: Pet) {
        petDataSet.add(pet)
        notifyObservers()
    }

    override suspend fun delete(pet: Pet) {
        petDataSet.remove(pet)
        notifyObservers()
    }

    override suspend fun deleteAllPetData() {
        petDataSet.clear()
        notifyObservers()
    }

    override suspend fun getById(id: Int): Pet =
        petDataSet.find { it.id == id } ?: throw Exception("PetData not found")


    override fun getAllPetData(): LiveData<List<Pet>> {
        return liveData
    }

    private fun notifyObservers() {
        liveData.value = getPetDataCopySortedById(petDataSet)


    }

    private fun getPetDataCopySortedById(petCollection: Collection<Pet>): MutableList<Pet> {
        val list = mutableListOf<Pet>()
        petCollection.forEach { originalPetData ->
            list.add(originalPetData.getCopy())
        }
        list.sortBy { it.id }
        return list
    }

}