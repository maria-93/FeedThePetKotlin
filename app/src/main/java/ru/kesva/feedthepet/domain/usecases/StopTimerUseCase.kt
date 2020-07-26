package ru.kesva.feedthepet.domain.usecases

import javax.inject.Inject

var shouldStopGlobal: Boolean = false

class StopTimerUseCase @Inject constructor() {

    fun stopTimer() {
        shouldStop = true
    }

    fun stopCountDownTimer() {
        shouldStopGlobal = true
    }
}