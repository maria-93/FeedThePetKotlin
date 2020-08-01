package ru.kesva.feedthepet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kesva.feedthepet.domain.model.PetData
import java.util.*
import kotlin.Comparator
import kotlin.reflect.KClass

open class FakeDatabase {
    /**
     * Отсортированное по id множество записей о животных в БД
     */
    protected val pets: SortedSet<PetData> =
        sortedSetOf(Comparator { o1, o2 -> (o1.id - o2.id) })

    private val _petDataLiveData = MutableLiveData<MutableList<PetData>>()
    protected val petDataLiveData: LiveData<MutableList<PetData>>
        get() {
            notifyObservers(PetData::class)
            return _petDataLiveData
        }

    private fun assignNewIdAndReturn(petData: PetData, set: SortedSet<PetData>): PetData {
        val maxId: Int = set.maxBy { it.id }?.id ?: 0
        petData.id = maxId + 1
        return petData
    }

    protected fun notifyObservers(clazz: KClass<out Any>) {
        if (clazz == PetData::class) {
            _petDataLiveData.value = getPetDataCopySortedById(pets)
        }

    }

    private fun getPetDataCopySortedById(petDataCollection: Collection<PetData>): MutableList<PetData> {
        val list = mutableListOf<PetData>()
        petDataCollection.forEach { originalPetData ->
            list.add(originalPetData.getCopy())
        }
        list.sortBy { it.id }
        return list
    }

    protected fun SortedSet<PetData>.insert(petData: PetData, needToNotify: Boolean) {
        this.add(assignNewIdAndReturn(petData, this))
        if (needToNotify) {
            notifyObservers(PetData::class)
        }
    }

    protected fun SortedSet<PetData>.insertAll(collection: Collection<PetData>) {
        var isPetInserted = false
        collection.forEach {
            val petData = assignNewIdAndReturn(it, this)
            this.add(petData)
            isPetInserted = true
        }
        if (isPetInserted) {
            notifyObservers(PetData::class)
        }
    }

    protected fun SortedSet<PetData>.remove(petData: PetData, needToNotify: Boolean) {
        this.remove(petData)
        if (needToNotify) {
            notifyObservers(PetData::class)
        }
    }

    protected fun SortedSet<PetData>.clear(needToNotify: Boolean) {
        this.clear()
        if (needToNotify) {
            notifyObservers(PetData::class)
        }
    }

    protected fun SortedSet<PetData>.removeAll(collection: Collection<PetData>, needToNotify: Boolean) {
        this.removeAll(collection)
        if (needToNotify) {
            notifyObservers(PetData::class)
        }
    }

    protected fun SortedSet<PetData>.update(petData: PetData){
        var isUpdated = false
        if (this.contains(petData)) {
            this.remove(petData)
            this.add(petData)
            isUpdated = true
        }
        if (isUpdated) {
            notifyObservers(PetData::class)
        }
    }

    protected fun SortedSet<PetData>.updateAll(collection: Collection<PetData>) {
        var isUpdated = false
        collection.forEach { petData ->
            if (this.contains(petData)) {
                this.remove(petData)
                this.add(petData)
                isUpdated = true
            }
        }
        if (isUpdated) {
            notifyObservers(PetData::class)
        }

    }


}