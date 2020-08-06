package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject


class AddNewPetUseCase @Inject constructor(private val repository: Repository) {
    suspend fun addNewPet(pet: Pet) {
        repository.insert(pet)
    }
}