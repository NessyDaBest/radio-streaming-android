package com.nemo.imaginaradio.views

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nemo.imaginaradio.databinding.FragmentPlayerBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PlayerFragment : Fragment() {

    var _binding:FragmentPlayerBinding? = null
    val binding:FragmentPlayerBinding
        get() = checkNotNull(_binding)

    private var mediaPlayer: MediaPlayer? = null
    private val audioUrl = "https://streaming.enacast.com/imaginaradioHD.mp3"
    private var state: String = "No inicializado"

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater,container)
        initializeView()
        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentPlayerBinding.inflate(inflater,container,false)
    }

    private fun initializeView() {
        playAudio()
        initializeButton()
        initializeTime()
    }

    private fun playAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                // URL del MP3 en streaming
                setDataSource(audioUrl)
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
            Log.d("MENSAJE","Crea")
        } else {
            // Reanuda si ya existe
            mediaPlayer?.setVolume(1f, 1f)
            Log.d("MENSAJE","Continua")
        }
        state = "Reproduciendo"
    }

    private fun stopAudio() {
        // Libera recursos
        mediaPlayer?.setVolume(0f, 0f)
        state = "Pausado"
        Log.d("MENSAJE","Para")
    }

    private fun initializeButton() {
        binding.imgState.setOnClickListener {
            when (state) {
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
                binding.txtHourB.text = actualHour.format(hourFormat)
                binding.txtHourA.text = nextHour.format(hourFormat)

                // Actualizar el progreso de los minutos
                binding.pbMinutes.progress = now.minute

                // Volver a ejecutar cada minuto
                handler.postDelayed(this, 1000L)
            }
        })
    }
}



/*
    btnPlay.setOnClickListener { playAudio() }
        btnStop.setOnClickListener { stopAudio() }
    }

    private fun playAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(audioUrl)   // URL del MP3 en streaming
                prepareAsync()            // Prepara el audio en segundo plano
                setOnPreparedListener {
                    start()               // Reproduce el audio cuando esté listo
                }
                setOnCompletionListener {
                    stopAudio()          // Limpia recursos al finalizar
                }
            }
        } else {
            mediaPlayer?.start()          // Reanuda si ya existe
        }
    }

    private fun stopAudio() {
        mediaPlayer?.release()            // Libera recursos
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAudio()                       // Limpia recursos al cerrar la app
    }
* */