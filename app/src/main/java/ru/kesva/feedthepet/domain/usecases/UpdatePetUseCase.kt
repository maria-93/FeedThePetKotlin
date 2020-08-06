package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class UpdatePetUseCase @Inject constructor(private val repository: Repository) {
    suspend fun updatePet(pet: Pet) {
        repository.update(pet)
    }
}