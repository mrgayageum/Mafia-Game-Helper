package com.mrgayageum.mafiagamehelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrgayageum.mafiagamehelper.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {
    var binding: FragmentMusicBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}