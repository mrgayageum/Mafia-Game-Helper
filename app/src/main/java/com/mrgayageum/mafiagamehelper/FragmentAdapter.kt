package com.mrgayageum.mafiagamehelper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fActivity: FragmentActivity) : FragmentStateAdapter(fActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MusicFragment()
            1 -> PregameFragment()
            else -> GameFragment()
        }
}