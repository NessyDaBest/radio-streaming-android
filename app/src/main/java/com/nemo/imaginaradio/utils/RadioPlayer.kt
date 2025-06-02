package com.nemo.imaginaradio.utils

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.nemo.imaginaradio.databinding.FragmentPlayerBinding
import com.nemo.imaginaradio.viewmodels.PlayerViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.WeakHashMap

//object to make it only instantiate once
object RadioPlayer {
    private val handler = Handler(Looper.getMainLooper())
    private var initialized = false
    private lateinit var viewModel: PlayerViewModel

    private val buttonList = mutableListOf<ImageView>()
    private val observedOwners = WeakHashMap<LifecycleOwner, Unit>()

    fun initialize(viewModel: PlayerViewModel, button: ImageView, lifecycleOwner: LifecycleOwner) {
        if (!initialized) {
            this.viewModel = viewModel
            initializeTime()
            initializePlayer()
            initialized = true
        }

        observePlayerState(lifecycleOwner)

        registerButton(button)
        //initializeButton(button, lifecycleOwner)
    }

    private fun initializePlayer() {
        viewModel.mediaPlayer = MediaPlayer().apply {
            // URL del MP3 en streaming
            setDataSource(viewModel.audioUrl)
            // Prepara el audio en segundo plano
            prepareAsync()
            setOnPreparedListener {
                // Reproduce el audio cuando esté listo
                start()
            }
            setOnCompletionListener {
                // Limpia recursos al finalizar
                stopAudio()
            }
        }
        viewModel.updatePlayerState("Reproduciendo")
        Log.d("MENSAJE","Crea")
    }

    fun playAudio() {
        // Reanuda si ya existe
        viewModel.mediaPlayer?.setVolume(1f, 1f)
        Log.d("MENSAJE","Continua")
        viewModel.updatePlayerState("Reproduciendo")
    }

    fun stopAudio() {
        // Libera recursos
        viewModel.mediaPlayer?.setVolume(0f, 0f)
        viewModel.updatePlayerState("Pausado")
        Log.d("MENSAJE","Para")
    }

    /*private fun initializeButton(button:ImageView, lifecycleOwner: LifecycleOwner) {
        viewModel.playerState.observe(lifecycleOwner) { newState ->
            when (viewModel.playerState.value) {
                "Reproduciendo" -> {
                    button.setImageResource(android.R.drawable.ic_media_play)
                }
                "Pausado" -> {
                    button.setImageResource(android.R.drawable.ic_media_pause)
                }
            }
        }

        button.setOnClickListener {
            when (viewModel.playerState.value) {
                "Reproduciendo" -> {
                    stopAudio()
                    button.setImageResource(android.R.drawable.ic_media_play)
                }
                "Pausado" -> {
                    playAudio()
                    button.setImageResource(android.R.drawable.ic_media_pause)
                }
            }
        }
    }*/

    private fun registerButton(button: ImageView) {
        if (!buttonList.contains(button)) {
            buttonList.add(button)
        }

        button.setOnClickListener {
            when (viewModel.playerState.value) {
                "Reproduciendo" -> stopAudio()
                "Pausado" -> playAudio()
            }
        }

        // Aplica el icono actual al botón
        updateButtonIcon(button, viewModel.playerState.value)
    }

    private fun observePlayerState(lifecycleOwner: LifecycleOwner) {
        if (observedOwners.containsKey(lifecycleOwner)) return
        observedOwners[lifecycleOwner] = Unit

        viewModel.playerState.observe(lifecycleOwner) { state ->
            Log.d("RadioPlayer", "Estado cambió a: $state")
            buttonList.forEach { updateButtonIcon(it, state) }
        }
    }

    private fun updateButtonIcon(button: ImageView, state: String?) {
        val icon = when (state) {
            "Reproduciendo" -> android.R.drawable.ic_media_pause
            "Pausado" -> android.R.drawable.ic_media_play
            else -> android.R.drawable.ic_media_play
        }
        button.setImageResource(icon)
    }

    private fun initializeTime() {
        handler.post(object : Runnable {
            override fun run() {
                // Obtener la hora actual
                val now = LocalTime.now()

                // Formatear la hora actual a "HH:00"
                val actualHour = now.withMinute(0).withSecond(0)
                val nextHour = actualHour.plusHours(1)

                // Formatear las horas para mostrar en los TextView
                val hourFormat = DateTimeFormatter.ofPattern("HH:mm")
                viewModel.updateActualHour(actualHour.format(hourFormat))
                viewModel.updateNextHour(nextHour.format(hourFormat))

                // Actualizar el progreso de los minutos
                viewModel.updateProgress(now.minute)

                // Volver a ejecutar cada minuto
                Log.d("MENSAJE","minuto ${now.minute}")
                handler.postDelayed(this, 60000L)
            }
        })
    }
}