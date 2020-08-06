package ru.kesva.feedthepet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kesva.feedthepet.domain.model.Pet
import java.util.*
import kotlin.Comparator
import kotlin.reflect.KClass

open class FakeDatabase {
    /**
     * Отсортированное по id множество записей о животных в БД
     */
    protected val pets: SortedSet<Pet> =
        sortedSetOf(Comparator { o1, o2 -> (o1.id - o2.id) })

    private val _petDataLiveData = MutableLiveData<MutableList<Pet>>()
    protected val petDataLive: LiveData<MutableList<Pet>>
        get() {
            notifyObservers(Pet::class)
            return _petDataLiveData
        }

    private fun assignNewIdAndReturn(pet: Pet, set: SortedSet<Pet>): Pet {
        val maxId: Int = set.maxBy { it.id }?.id ?: 0
        pet.id = maxId + 1
        return pet
    }

    protected fun notifyObservers(clazz: KClass<out Any>) {
        if (clazz == Pet::class) {
            _petDataLiveData.value = getPetDataCopySortedById(pets)
        }

    }

    private fun getPetDataCopySortedById(petCollection: Collection<Pet>): MutableList<Pet> {
        val list = mutableListOf<Pet>()
        petCollection.forEach { originalPetData ->
            list.add(originalPetData.getCopy())
        }
        list.sortBy { it.id }
        return list
    }

    protected fun SortedSet<Pet>.insert(pet: Pet, needToNotify: Boolean) {
        this.add(assignNewIdAndReturn(pet, this))
        if (needToNotify) {
            notifyObservers(Pet::class)
        }
    }

    protected fun SortedSet<Pet>.insertAll(collection: Collection<Pet>) {
        var isPetInserted = false
        collection.forEach {
            val petData = assignNewIdAndReturn(it, this)
            this.add(petData)
            isPetInserted = true
        }
        if (isPetInserted) {
            notifyObservers(Pet::class)
        }
    }

    protected fun SortedSet<Pet>.remove(pet: Pet, needToNotify: Boolean) {
        this.remove(pet)
        if (needToNotify) {
            notifyObservers(Pet::class)
        }
    }

    protected fun SortedSet<Pet>.clear(needToNotify: Boolean) {
        this.clear()
        if (needToNotify) {
            notifyObservers(Pet::class)
        }
    }

    protected fun SortedSet<Pet>.removeAll(collection: Collection<Pet>, needToNotify: Boolean) {
        this.removeAll(collection)
        if (needToNotify) {
            notifyObservers(Pet::class)
        }
    }

    protected fun SortedSet<Pet>.update(pet: Pet){
        var isUpdated = false
        if (this.contains(pet)) {
            this.remove(pet)
            this.add(pet)
            isUpdated = true
        }
        if (isUpdated) {
            notifyObservers(Pet::class)
        }
    }

    protected fun SortedSet<Pet>.updateAll(collection: Collection<Pet>) {
        var isUpdated = false
        collection.forEach { petData ->
            if (this.contains(petData)) {
                this.remove(petData)
                this.add(petData)
                isUpdated = true
            }
        }
        if (isUpdated) {
            notifyObservers(Pet::class)
        }

    }


}