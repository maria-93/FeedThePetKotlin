package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class DeletePetUseCase @Inject constructor(
    private val repository: Repository,
    private val alarmRepository: AlarmRepository
) {

    suspend fun deletePet(pet: Pet) {
        alarmRepository.cancelAlarm(pet)
        repository.delete(pet)
    }
}