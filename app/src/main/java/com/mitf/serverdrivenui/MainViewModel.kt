package com.mitf.serverdrivenui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData("")
}