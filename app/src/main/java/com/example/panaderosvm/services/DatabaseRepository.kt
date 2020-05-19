package com.example.panaderosvm.services

import android.content.Context
import android.util.Log
import com.example.panaderosvm.model.local.AppDatabase
import com.example.panaderosvm.model.local.panaderias.PanaderiasDAO
import com.example.panaderosvm.model.local.panaderias.PanaderiasEntity
import com.example.panaderosvm.model.local.panaderias.ParseJsonPanaderias
import com.example.panaderosvm.model.local.pueblos.ParseJsonPueblos
import com.example.panaderosvm.model.local.pueblos.PueblosDAO
import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import com.example.panaderosvm.model.repoitorios.PuebloRepository
import com.example.panaderosvm.services.remote.base.OnResponse
import com.example.panaderosvm.utils.encrypt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DatabaseRepository(private var context: Context) : CoroutineScope {

    private val TAG: String = DatabaseRepository::class.java.simpleName

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var mPueblosRepository: PuebloRepository = PuebloRepository(context)

    var mPueblosDAO: PueblosDAO
    var mPanaderiasDAO: PanaderiasDAO

    init {
        AppDatabase.getInstance(context).let {

            mPueblosDAO = it.pueblosDao()
            mPanaderiasDAO = it.panaderiasDao()

        }
    }

    fun populateDatabase(authToken: String) {
        try {
            // SYNCS AQUI
            populatePueblos(authToken)

        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    fun populateDatabaseLocal() {
        try {
            // SYNCS AQUI
            populatePueblos()
            populatePanaderias()

        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun populatePueblos(authToken: String) {
        launch {
            mPueblosRepository.syncPueblos(authToken, object : OnResponse<Any> {
                override fun onResponse(
                    responseType: OnResponse.ResponseType,
                    entity: Any?,
                    listEntity: List<Any>?
                ) {
                    Log.e(TAG, "$responseType.name pueblos pre - cargados correctamente")
                }

                override fun onError(code: Int, error: String?) {
                    Log.e(TAG, "$error, // Error al pre-cargar pueblos")
                }
            })
        }
    }

    private fun populatePanaderias(authToken: String) {
        launch {
            /*mPueblosRepository.syncPanaderias(authToken, object : OnResponse<Any> {
                override fun onResponse(
                    responseType: OnResponse.ResponseType,
                    entity: Any?,
                    listEntity: List<Any>?
                ) {
                    Log.e(TAG, "$responseType.name panaderias pre - cargados correctamente")
                }

                override fun onError(code: Int, error: String?) {
                    Log.e(TAG, "$error, // Error al pre-cargar panaderias")
                }
            })*/
        }
    }

    private fun populatePueblos() {
        val listPueblos =
            ParseJsonPueblos(context).parseJson()
        if (listPueblos != null) {
            launch {
                insertPueblos(listPueblos)
            }
        }
    }

    private fun populatePanaderias() {
        val listPanaderias =
            ParseJsonPanaderias(context).parseJson()
        if (listPanaderias != null) {
            launch {
                insertPanaderias(listPanaderias)
            }
        }
    }

    private suspend fun insertPueblos(listPuertos: MutableList<PueblosEntity>?) {
        withContext(Dispatchers.IO)
        {
            if (listPuertos != null) {
                mPueblosDAO.insertPueblos(listPuertos)
            }
        }
    }

    private suspend fun insertPanaderias(listPanaderias: MutableList<PanaderiasEntity>?) {
        withContext(Dispatchers.IO)
        {
            if (listPanaderias != null) {
                mPanaderiasDAO.insertPanaderias(listPanaderias)
            }
        }
    }

}