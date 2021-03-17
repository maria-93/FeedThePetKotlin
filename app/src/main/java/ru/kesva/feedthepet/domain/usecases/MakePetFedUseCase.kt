package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class MakePetFedUseCase @Inject constructor(
    private val repository: Repository,
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(pet: Pet) {
        val clonedPet = pet.getCopy()
        val timeInFuture = System.currentTimeMillis() + clonedPet.timeInterval
        clonedPet.timeInFuture = timeInFuture
        repository.update(clonedPet)
        alarmRepository.startAlarm(clonedPet, clonedPet.timeInFuture)
    }
}