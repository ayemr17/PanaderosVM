package com.example.panaderosvm._view_ui.panaderias

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.panaderosvm._view_ui.Base.BaseViewModel
import com.example.panaderosvm.model.local.panaderias.PanaderiasEntity
import com.example.panaderosvm.model.repoitorios.PanaderiasRepository
import com.example.panaderosvm.utils.ResponseObjetBasic
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PanaderiasViewModel(application: Application) : BaseViewModel(application), CoroutineScope {

    private val job: Job = SupervisorJob()
    private var panaderiasRepository = PanaderiasRepository(application)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var panaderiasList: MutableLiveData<List<PanaderiasEntity>> = MutableLiveData()
    var panaderiasPerPuebloList: MutableLiveData<List<PanaderiasEntity>> = MutableLiveData()

    fun getPanaderias() {
        launch {
            panaderiasRepository.getPanaderias(object : ResponseObjetBasic<List<PanaderiasEntity>> {
                override fun onSuccess(entity: List<PanaderiasEntity>) {
                    if (!entity.isEmpty()) {
                        panaderiasList.postValue(entity)
                    }
                }

                override fun onError(message: String) {
                    error.postValue(message)
                }
            })
        }
    }

    fun getPanaderiasPerPueblo(idPueblo : Int) {
        launch {
            panaderiasRepository.getPanaderiasWithIdPueblo(idPueblo, object : ResponseObjetBasic<List<PanaderiasEntity>> {
                override fun onSuccess(entity: List<PanaderiasEntity>) {
                    if (!entity.isEmpty()) {
                        panaderiasPerPuebloList.postValue(entity)
                    }
                }

                override fun onError(message: String) {
                    error.postValue(message)
                }
            })
        }
    }
}