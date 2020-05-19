package com.example.panaderosvm._view_ui.login

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.example.panaderosvm._view_ui.Base.BaseViewModel
import com.example.panaderosvm.services.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : BaseViewModel(application), CoroutineScope {

    private val job: Job = SupervisorJob()
    private var mDatabaseRepository: DatabaseRepository? = DatabaseRepository(application)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun crearDatabase() {
        try {
            mDatabaseRepository?.populateDatabaseLocal()
        } catch (e: Exception) {
            Log.e(TAG, "Error en la pre-carga de la base de datos: ${e.message}")
        }
    }
}