package com.mrgayageum.mafiagamehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.mrgayageum.mafiagamehelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pager = binding.pager
        pager.offscreenPageLimit = 3
        pager.adapter = FragmentAdapter(this)
        pager.setCurrentItem(1, false)
    }
}