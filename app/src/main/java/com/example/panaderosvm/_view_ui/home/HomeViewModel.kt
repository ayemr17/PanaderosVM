package com.example.panaderosvm._view_ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.panaderosvm._view_ui.Base.BaseViewModel
import com.example.panaderosvm._view_ui.pueblos.PueblosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class HomeViewModel(application: Application) : BaseViewModel(application), CoroutineScope {

    private var pueblosViewModel = PueblosViewModel(application)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}