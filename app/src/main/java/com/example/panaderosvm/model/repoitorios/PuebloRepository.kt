package com.example.panaderosvm.model.repoitorios

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.example.panaderosvm.model.local.AppDatabase
import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import com.example.panaderosvm.model.remote.PueblosPOJO
import com.example.panaderosvm.services.remote.base.*
import com.example.panaderosvm.services.remote.networking.PueblosAPI
import com.example.panaderosvm.utils.ResponseObjetBasic
import com.example.panaderosvm.utils.decrypt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PuebloRepository(context: Context) : BaseRemoteRepository(), CoroutineScope {

    private val mDatabase = AppDatabase.getInstance(context)
    private var mPuebloDao = mDatabase.pueblosDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    companion object {
        val TAG = PuebloRepository.javaClass.simpleName
    }

    private val BASE_URL = "http://10.152.16.231:91/sostenibilidad/"
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
    }

    suspend fun savePueblos(pueblosList: List<PueblosEntity>) =
        withContext(Dispatchers.IO) {
            mPuebloDao.insertPueblos(pueblosList)
        }

    @WorkerThread
    suspend fun getPueblos(responseObjetBasic: ResponseObjetBasic<List<PueblosEntity>>) =
        withContext(Dispatchers.IO) {
            val listPueblos: List<PueblosEntity> = mPuebloDao.getAllPueblos()
            if (listPueblos.isNotEmpty()) {
                /*for (odsEntity in listPueblos) {
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                }*/
            }
            responseObjetBasic.onSuccess(listPueblos)
        }

    @WorkerThread
    suspend fun getSinglePuebloWithId(id : Int, responseObjetBasic: ResponseObjetBasic<PueblosEntity>) =
        withContext(Dispatchers.IO) {
            val pueblo: PueblosEntity? = mPuebloDao.getSinglePuebloWithId(id)
            if (pueblo != null) {
                /*for (odsEntity in listPueblos) {
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                }*/
            }
            responseObjetBasic.onSuccess(pueblo!!)
        }

}