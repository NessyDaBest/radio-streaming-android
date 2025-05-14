package com.nemo.imaginaradio.viewmodels

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {
    var mediaPlayer: MediaPlayer? = null
    val audioUrl = "https://streaming.enacast.com/imaginaradioHD.mp3"

    //MutableLiveData
    private val _playerState = MutableLiveData<String>("No inicializado")
    private val _progress = MutableLiveData<Int>()
    private val _actualHour = MutableLiveData<String>()
    private val _nextHour = MutableLiveData<String>()

    //LiveData
    val playerState: LiveData<String> = _playerState
    val progress: LiveData<Int> = _progress
    val actualHour: LiveData<String> = _actualHour
    val nextHour: LiveData<String> = _nextHour

    //Update methods for MutableLiveData
    fun updatePlayerState(value: String) {
        _playerState.value = value
    }
    fun updateProgress(value: Int) {
        _progress.value = value
    }
    fun updateActualHour(value: String) {
        _actualHour.value = value
    }
    fun updateNextHour(value: String) {
        _nextHour.value = value
    }
}