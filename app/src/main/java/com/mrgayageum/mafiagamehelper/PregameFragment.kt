package com.mrgayageum.mafiagamehelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.mrgayageum.mafiagamehelper.databinding.FragmentPregameBinding

class PregameFragment : Fragment() {
    private var binding: FragmentPregameBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPregameBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonRandom?.setOnClickListener {
            GameManager.getRoles()
            setDistributionView()
            binding?.buttonStart?.isEnabled = true
        }

        binding?.buttonStart?.setOnClickListener {
            GameManager.startGame()
            binding?.buttonEnd?.isEnabled = true
            binding?.buttonStart?.isEnabled = false
            binding?.buttonRandom?.isEnabled = false
        }

        binding?.buttonEnd?.setOnClickListener {
            GameManager.endGame()
            binding?.buttonRandom?.isEnabled = true
            binding?.buttonEnd?.isEnabled = false

        }

        binding?.seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val players = progress + 6
                GameManager.playersNumber = players
                binding?.playersNum?.text = players.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setDistributionView() {
        for (i in 0 until GameManager.playersNumber) {
            val pos = binding?.positionRow?.getChildAt(i) as TextView?
            pos?.text = GameManager.positions[i]
            val role = binding?.roleRow?.getChildAt(i) as ImageView?
            role?.setImageResource(GameManager.roleRes[GameManager.roles[i]])
        }

        for (i in GameManager.playersNumber until GameManager.maxPlayers) {
            val pos = binding?.positionRow?.getChildAt(i) as TextView?
            pos?.text = ""
            val role = binding?.roleRow?.getChildAt(i) as ImageView?
            role?.setImageResource(android.R.color.transparent)
        }

        if (GameManager.ghost) {
            val role = binding?.roleRow?.getChildAt(GameManager.playersNumber) as ImageView?
            role?.setImageResource(R.mipmap.role_ghost)
        }
    }

}