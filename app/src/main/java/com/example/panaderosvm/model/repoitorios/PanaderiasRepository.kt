package com.example.panaderosvm.model.repoitorios

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.panaderosvm.model.local.AppDatabase
import com.example.panaderosvm.model.local.panaderias.PanaderiasEntity
import com.example.panaderosvm.services.remote.base.BaseRemoteRepository
import com.example.panaderosvm.utils.ResponseObjetBasic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PanaderiasRepository(context: Context) : BaseRemoteRepository(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val mDatabase = AppDatabase.getInstance(context)
    private var mPanaderiasDao = mDatabase.panaderiasDao()

    /*private val BASE_URL = "http://10.152.16.231:91/sostenibilidad/"
    private var apiService: PueblosAPI =
        ServiceGenerator.createService(BASE_URL, null, null, PueblosAPI::class.java)


    suspend fun getTerritoriesApi(
        token: String,
        remoteErrorEmiter: RemoteErrorEmitter
    ): List<PueblosPOJO>? {
        return safeApiCall(remoteErrorEmiter) {
            apiService.getPueblos(token)
        }
    }

    suspend fun syncPueblos(token: String, onResponse: OnResponse<Any>) {
        getTerritoriesApi(token, object :
            RemoteErrorEmitter {
            override fun onError(msg: String) {
                Log.d(TAG, "error:  $msg")
                onResponse.onError(0, msg)
            }

            override fun onError(errorType: ErrorType) {
                Log.d(TAG, "error:  ${errorType.name}")
                onResponse.onError(0, errorType.name)
            }
        })?.let { list ->
            val auxList: MutableList<PueblosEntity> = ArrayList()
            list.forEach {
                auxList.add(it.toEntity())
            }
            if (auxList.isNotEmpty())
                savePueblos(auxList)
        }
    }*/

    suspend fun savePueblos(ppanaderiasList: List<PanaderiasEntity>) =
        withContext(Dispatchers.IO) {
            mPanaderiasDao.insertPanaderias(ppanaderiasList)
        }

    @WorkerThread
    suspend fun getPanaderias(responseObjetBasic: ResponseObjetBasic<List<PanaderiasEntity>>) =
        withContext(Dispatchers.IO) {
            val listPanaderias: List<PanaderiasEntity>? = mPanaderiasDao.getAllPanaderias()
            if (listPanaderias != null) {
                if (listPanaderias.isNotEmpty()) {
                    /*for (odsEntity in listPueblos) {
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                        }*/
                }
            }
            listPanaderias?.let {
                responseObjetBasic.onSuccess(it)
            }
        }

    @WorkerThread
    suspend fun getPanaderiasWithIdPueblo(idPueblo: Int, responseObjetBasic: ResponseObjetBasic<List<PanaderiasEntity>>) =
        withContext(Dispatchers.IO) {
            val listPanaderias: List<PanaderiasEntity>? = mPanaderiasDao.getPanaderiasWithIdPueblo(idPueblo)
            if (listPanaderias != null) {
                if (listPanaderias.isNotEmpty()) {
                    /*for (odsEntity in listPueblos) {
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                            odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                        }*/
                }
            }
            if (listPanaderias != null) {
                responseObjetBasic.onSuccess(listPanaderias)
            }
        }
}
