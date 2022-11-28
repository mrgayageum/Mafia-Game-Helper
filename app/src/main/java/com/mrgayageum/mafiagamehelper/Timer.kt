package com.mrgayageum.mafiagamehelper

import android.os.CountDownTimer
import android.widget.TextView

class Timer(val countDownInterval: Long = 10) {
    var textScreen: TextView? = null
    private var active = false
    private var paused = false

    var timeRemaining = 0L
        private set
    
    fun isActive() = active
    fun isPaused() = paused

    fun activate() {
        active = true
        paused = false

        object : CountDownTimer(timeRemaining, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if (!active || paused) {
                    cancel()
                } else {
                    if (millisUntilFinished / 1000 < (millisUntilFinished + countDownInterval) / 1000) {
                        textScreen?.text = String.format("%02d", millisUntilFinished / 1000)
                    }
                    timeRemaining = millisUntilFinished
                }
            }

            override fun onFinish() {
                active = false
                paused = false
                textScreen?.text = "‒‒"
            }
        }.start()
    }

    fun start(seconds: Long) {
        timeRemaining = seconds * 1000 + countDownInterval + 1
        activate()
    }

    fun pause() {
        paused = true
    }

    fun addTime(seconds: Long) {
        if (paused) {
            timeRemaining += seconds * 1000
        } else {
            pause()
            timeRemaining += seconds * 1000
            activate()
        }
    }

    fun finish() {
        active = false
        paused = false
        textScreen?.text = "‒‒"
    }
}