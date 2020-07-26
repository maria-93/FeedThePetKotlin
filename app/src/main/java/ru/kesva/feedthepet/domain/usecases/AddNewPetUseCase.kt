package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject


class AddNewPetUseCase @Inject constructor(private val repository: Repository) {
    suspend fun addNewPet(petData: PetData) {
        repository.insert(petData)
    }
}