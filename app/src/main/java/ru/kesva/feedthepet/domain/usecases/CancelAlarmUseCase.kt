package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import javax.inject.Inject

class CancelAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository,
private val updatePetUseCase: UpdatePetUseCase) {
    suspend fun cancelAlarmFor(pet: Pet) {
        alarmRepository.cancelAlarmFor(pet)
        //чтобы время на вьюшке таймера после остановки будильника перестало обновляться,
        // у питомца нужно обнулить timeInFuture,
        //с помощью которого рассчитывается оставшееся до кормления время
        pet.timeInFuture = 0
        updatePetUseCase.updatePet(pet)
    }
}