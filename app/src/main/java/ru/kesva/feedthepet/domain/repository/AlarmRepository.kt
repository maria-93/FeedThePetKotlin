package ru.kesva.feedthepet.domain.repository

import ru.kesva.feedthepet.domain.model.Pet

interface AlarmRepository {
    fun startAlarm(
        pet: Pet,
        triggerTime: Long
    )

    fun cancelAlarmFor(pet: Pet)

    fun sendNotification(pet: Pet)
}