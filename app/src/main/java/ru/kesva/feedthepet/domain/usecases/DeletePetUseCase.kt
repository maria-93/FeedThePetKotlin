package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class DeletePetUseCase @Inject constructor(private val repository: Repository) {
    suspend fun deletePet(petData: PetData) {
        repository.delete(petData)
    }
}