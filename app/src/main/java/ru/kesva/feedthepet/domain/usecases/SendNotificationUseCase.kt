package ru.kesva.feedthepet.domain.usecases

import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    fun sendNotification(pet: Pet) {
        alarmRepository.sendNotification(pet)
    }
}