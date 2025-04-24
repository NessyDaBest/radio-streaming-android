package com.nemo.imaginaradio.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.nemo.imaginaradio.R
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.NavigationUI
import com.nemo.imaginaradio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startBinding()
        setupNavbar()
    }

    fun getNavController(): NavController{
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        return navHostFragment.navController
    }

    private fun startBinding(){
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun setupNavbar(){
        NavigationUI.setupWithNavController(binding.mainNavbar, getNavController())
    }
}