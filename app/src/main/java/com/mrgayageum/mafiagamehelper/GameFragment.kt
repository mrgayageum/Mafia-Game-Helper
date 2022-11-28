package com.mrgayageum.mafiagamehelper

import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.mrgayageum.mafiagamehelper.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var binding: FragmentGameBinding? = null

    private lateinit var actionButtons: Array<ImageButton?>

    private val timer = Timer()
    private val actions = arrayOf(::nightKill, ::checkDon, ::checkSheriff, ::giveWarn, ::addOnVoting, ::deletePlayer)

    private var currentAction: (Int) -> Unit = { }

    private lateinit var theme: Resources.Theme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        theme = requireActivity().theme
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer.textScreen = binding?.timerText

        actionButtons = arrayOf(binding?.kill, binding?.donCheck, binding?.sheriffCheck,
                                binding?.warn, binding?.add, binding?.delete)

        binding?.minute?.setOnClickListener { if (GameManager.started) { timerStart(60) } }
        binding?.halfMinute?.setOnClickListener { if (GameManager.started) { timerStart(30) } }
        binding?.pause?.setOnClickListener { if (GameManager.started) { timerPause() } }
        binding?.skip?.setOnClickListener { if (GameManager.started) { timerStop() } }
        binding?.addTime?.setOnClickListener { if (GameManager.started) { timer.addTime(5) } }

        binding?.cancelVoting?.setOnClickListener { if (GameManager.started) { clear() } }

        for (i in 0 until GameManager.actions) {
            actionButtons[i]?.setOnClickListener { if (GameManager.started) { changeAction(i) } }
        }

        for (i in 0 until GameManager.playersNumber) {
            val playerButton = binding?.rowButton?.getChildAt(i) as Button
            playerButton.setOnClickListener { if (GameManager.started) { currentAction(i) } }
        }

        setState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.textScreen = null
        binding = null
    }

    private fun setState() {
        if (timer.timeRemaining == 0L) {
            binding?.timerText?.text = "‒‒"
            binding?.pause?.setImageResource(R.drawable.ic_baseline_pause)
        } else {
            binding?.timerText?.text = String.format("%02d", timer.timeRemaining / 1000)
            binding?.pause?.setImageResource(R.drawable.ic_baseline_play_arrow)
        }

        for (i in 0 until GameManager.maxPlayers) {}
    }

    private fun timerStart(seconds: Long) {
        if (!timer.isActive()) {
            timer.start(seconds)
        }
    }

    private fun timerPause() {
        if (!timer.isActive()) {
            return
        }
        if (timer.isPaused()) {
            timer.activate()
            binding?.pause?.setImageResource(R.drawable.ic_baseline_pause)
        } else {
            timer.pause()
            binding?.pause?.setImageResource(R.drawable.ic_baseline_play_arrow)
        }
    }

    private fun timerStop() {
        binding?.pause?.setImageResource(R.drawable.ic_baseline_pause)
        timer.finish()
    }

    private fun changeAction(actionId: Int) {
        for (i in 0 until GameManager.actions) {
            actionButtons[i]?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
        }
        currentAction = if (currentAction == actions[actionId]) {
            { }
        } else {
            actionButtons[actionId]?.background?.setTint(resources.getColor(R.color.color_control_activated, theme))
            actions[actionId]
        }
    }

    private fun nightKill(id: Int) {
        GameManager.kill(id)

        val playerButton = binding?.rowButton?.getChildAt(id) as Button
        playerButton.background?.setTint(resources.getColor(R.color.transparent, theme))

        currentAction = { }
        binding?.kill?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
    }

    private fun checkSheriff(id: Int) {
        currentAction = { }
        binding?.sheriffCheck?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
        val checked: ImageView = binding?.rowSpeak?.getChildAt(id) as ImageView

        val col = if (GameManager.checkSheriff(id)) {
            R.color.right
        } else {
            R.color.wrong
        }
        checked.setImageResource(col)

        object : CountDownTimer(2000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                if (currentAction == ::checkDon || currentAction == ::checkSheriff) {
                    checked.setImageResource(R.color.transparent)
                    cancel()
                }
            }
            override fun onFinish() {
                checked.setImageResource(R.color.transparent)
            }
        }.start()
    }

    private fun checkDon(id: Int) {
        currentAction = { }
        binding?.donCheck?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
        val checked: ImageView = binding?.rowSpeak?.getChildAt(id) as ImageView

        val col = if (GameManager.checkDon(id)) {
            R.color.right
        } else {
            R.color.wrong
        }
        checked.setImageResource(col)

        object : CountDownTimer(2000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                if (currentAction == ::checkDon || currentAction == ::checkSheriff) {
                    checked.setImageResource(R.color.transparent)
                    cancel()
                }
            }
            override fun onFinish() {
                checked.setImageResource(R.color.transparent)
            }
        }.start()
    }

    private fun giveWarn(id: Int) {
        val playerButton = binding?.rowButton?.getChildAt(id) as Button
        val warnCount = binding?.rowWarns?.getChildAt(id) as ImageView
        GameManager.players[id].warns++
        if (GameManager.players[id].warns > 3) {
            playerButton.background?.setTint(resources.getColor(R.color.transparent, theme))
        } else {
            warnCount.setImageResource(GameManager.warnRes[GameManager.players[id].warns - 1])
        }

        currentAction = { }
        binding?.warn?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
    }

    private fun deletePlayer(id: Int) {
        binding?.addedForVoting?.text = ""
        val playerButton = binding?.rowButton?.getChildAt(id) as Button
        playerButton.background?.setTint(resources.getColor(R.color.transparent, theme))

        currentAction = { }
        binding?.delete?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
    }

    private fun addOnVoting(id: Int) {
        binding?.addedForVoting?.append("  " + (id + 1).toString())

        currentAction = { }
        binding?.add?.background?.setTint(resources.getColor(R.color.color_button_normal, theme))
    }

    private fun addToAccident(id: Int) {

    }
    private fun clear() {
        binding?.addedForVoting?.text = ""
    }
}