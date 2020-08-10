package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class DeletePetUseCase @Inject constructor(private val repository: Repository) {

    suspend fun deletePet(pet: Pet) {
        repository.cancelAlarm(pet.id)
        repository.delete(pet)
    }
}