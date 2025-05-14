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
import com.nemo.imaginaradio.databinding.FragmentPlayerBinding
import com.nemo.imaginaradio.viewmodels.PlayerViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//object to make it only instantiate once
object RadioPlayer {
    private val handler = Handler(Looper.getMainLooper())
    private var initialized = false
    private lateinit var viewModel: PlayerViewModel

    fun initialize(viewModel: PlayerViewModel, button: View) {
        if (!initialized) {
            this.viewModel = viewModel
            initializeTime()
            initializePlayer()
            initialized = true
        }
        initializeButton(button)
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

    private fun initializeButton(button:View) {
        button.setOnClickListener {
            when (viewModel.playerState.value) {
                "Reproduciendo" -> { stopAudio() }
                "Pausado" -> { playAudio() }
            }
        }
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