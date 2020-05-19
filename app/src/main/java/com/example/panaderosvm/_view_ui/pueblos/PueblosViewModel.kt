package com.example.panaderosvm._view_ui.pueblos

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.panaderosvm._view_ui.Base.BaseViewModel
import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import com.example.panaderosvm.model.repoitorios.PuebloRepository
import com.example.panaderosvm.utils.ResponseObjetBasic
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PueblosViewModel(application: Application) : BaseViewModel(application), CoroutineScope {

    private val _text = MutableLiveData<String>().apply {
        value = "PUEBLOSSSSSSSSSSSSSSSSS"
    }
    val text: LiveData<String> = _text

    private val job: Job = SupervisorJob()
    private var pueblosRepository = PuebloRepository(application)

    var pueblosList: MutableLiveData<List<PueblosEntity>> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

        fun getPueblos() {
            launch {
                pueblosRepository.getPueblos(object : ResponseObjetBasic<List<PueblosEntity>> {
                    override fun onSuccess(entity: List<PueblosEntity>) {
                        if (!entity.isEmpty()) {
                            pueblosList.postValue(entity)
                        }
                    }

                    override fun onError(message: String) {
                        error.postValue(message)
                    }
                })
            }
        }
}