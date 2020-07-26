package ru.kesva.feedthepet.domain.usecases

import android.content.Context
import android.widget.Toast
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository
import java.util.*
import javax.inject.Inject

class PetFedUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository,
    private val registerAlarmUseCase: RegisterAlarmUseCase
) {
    suspend fun petFed(petData: PetData) {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis + petData.timeInterval
        calendar.timeInMillis = endTime
        petData.calendar = calendar
        repository.update(petData)
        registerAlarmUseCase.registerAlarm(petData.id, calendar)
        showToastStartAlarm()
    }

    private fun showToastStartAlarm() {
        Toast.makeText(context, "Будильник запущен", Toast.LENGTH_SHORT).show()
    }
}