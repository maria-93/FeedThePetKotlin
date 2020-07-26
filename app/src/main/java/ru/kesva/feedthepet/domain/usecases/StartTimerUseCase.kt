package ru.kesva.feedthepet.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kesva.feedthepet.getFormattedTime
import ru.kesva.feedthepet.domain.model.PetData
import javax.inject.Inject

var shouldStop: Boolean = false


class StartTimerUseCase @Inject constructor(
) {
    suspend fun startTimer(petData: PetData) {
        val finalTime = petData.calendar.timeInMillis
        val timeForTv = petData.remainTime
        withContext(Dispatchers.IO) {
            while (true) {
                try {
                    val remainTime = finalTime - System.currentTimeMillis()
                    if (remainTime <= 0 || shouldStop || shouldStopGlobal) {
                        break
                    }
                    val parseTime = getFormattedTime(remainTime)
                    timeForTv.set(parseTime)
                    Thread.sleep(1000)
                } catch (exc: InterruptedException) {
                    exc.printStackTrace()
                    break
                }
            }
        }
    }
}
