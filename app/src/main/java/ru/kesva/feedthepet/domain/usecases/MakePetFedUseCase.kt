package ru.kesva.feedthepet.domain.usecases

import android.util.Log
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class MakePetFedUseCase @Inject constructor(
    private val repository: Repository,
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(pet: Pet) {
        Log.d("Test!", "petFedButtonClicked: ")
        val timeInFuture = System.currentTimeMillis() + pet.timeInterval
        pet.timeInFuture = timeInFuture
        repository.update(pet)
        Log.d("Test!", "invoke after update: pet ${pet.petName} futureTime ${pet.timeInterval}")
        alarmRepository.startAlarm(pet, pet.timeInFuture)
    }
}