package com.nemo.imaginaradio.views

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.nemo.imaginaradio.R
import kotlin.jvm.java

class SplashActivity : AppCompatActivity() {

    private var isAnimationCompleted = false
    private var hasStartedMain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageViewTop: ImageView = findViewById(R.id.imageViewTop)
        val imageViewBottom: ImageView = findViewById(R.id.imageViewBottom)

        // Ejecutar la animación después de 1 segundo (1000ms)
        Handler(Looper.getMainLooper()).postDelayed({
            animateImages(imageViewTop, imageViewBottom)
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        // Si la animación ya terminó y la actividad está visible, inicia MainActivity
        if (isAnimationCompleted && !hasStartedMain) {
            startMainActivity()
        }
    }

    private fun animateImages(top: ImageView, bottom: ImageView) {
        // Animación para mover la imagen superior hacia arriba
        ObjectAnimator.ofFloat(top, "translationY", -500f).apply {
            duration = 1000
            interpolator = DecelerateInterpolator()
            start()
        }

        // Animación para mover la imagen inferior hacia abajo
        ObjectAnimator.ofFloat(bottom, "translationY", 500f).apply {
            duration = 1000
            interpolator = DecelerateInterpolator()
            start()
        }

        // Marca que la animación se ha completado después de 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            isAnimationCompleted = true
            // Si la actividad ya está en primer plano, inicia MainActivity
            if (!isFinishing && hasWindowFocus()) {
                startMainActivity()
            }
        }, 2000)
    }

    private fun startMainActivity() {
        if (hasStartedMain) return // evitar llamadas repetidas
        hasStartedMain = true
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}