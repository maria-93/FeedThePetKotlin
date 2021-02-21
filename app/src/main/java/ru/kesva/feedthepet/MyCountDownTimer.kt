package ru.kesva.feedthepet

import android.os.CountDownTimer
import android.util.Log

class MyCountDownTimer {
    private var timer: CountDownTimer? = null
    var onTickListener: ((remainTime: Long) -> Unit)? = null
    var onFinishListener: (() -> Unit)? = null

    fun start(fromTime: Long) {
        stop()
        timer = object : CountDownTimer(fromTime, 1000) {
            override fun onFinish() {
                onFinishListener?.invoke()

            }

            override fun onTick(remainTime: Long) {
                onTickListener?.invoke(remainTime)
            }

        }
        timer?.start()
    }

    fun stop() {
        timer?.cancel()
    }
}