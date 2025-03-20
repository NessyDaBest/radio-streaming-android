package com.nemo.imaginaradio.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nemo.imaginaradio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        setContentView(binding.root)
    }

    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }
}