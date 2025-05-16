package com.nemo.imaginaradio.views

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var splashTriggered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplica el tema normal inmediatamente
        setTheme(R.style.Theme_ImaginaRadio)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura NavController
        val navHost = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        NavigationUI.setupWithNavController(binding.mainNavbar, navHost.navController)

        // Al cargar la vista, registra el toque
        binding.splashOverlay.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN && !splashTriggered) {
                splashTriggered = true
                animateSplash()
            }
            true
        }
    }

    private fun animateSplash() {
        val top = binding.imageViewTop
        val bottom = binding.imageViewBottom
        val overlay = binding.splashOverlay

        ObjectAnimator.ofFloat(top, "translationY", -top.height.toFloat())
            .setDuration(1000).apply {
                interpolator = DecelerateInterpolator()
                start()
            }

        ObjectAnimator.ofFloat(bottom, "translationY", bottom.height.toFloat())
            .setDuration(1000).apply {
                interpolator = DecelerateInterpolator()
                start()
            }

        // Oculta overlay tras la animación
        Handler(Looper.getMainLooper()).postDelayed({
            overlay.visibility = View.GONE
        }, 1000)
    }
}
