package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.nemo.imaginaradio.R
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.NavigationUI
import com.nemo.imaginaradio.databinding.ActivityMainBinding
import com.nemo.imaginaradio.viewmodels.PlayerViewModel

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        startBinding()
        setupNavbar()

        setContentView(binding.root)
        hideShowNavBar()
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

    private fun hideShowNavBar() {
        val navController = getNavController()
        val floatingPlayer: View = findViewById(R.id.floating_player_fragment_container)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    // Primer fragmento: ocultar minireproductor
                    floatingPlayer.visibility = View.GONE
                }
                else -> {
                    // Opcional: comportamiento por defecto
                    floatingPlayer.visibility = View.VISIBLE
                }
            }
        }
    }
}