package com.example.panaderosvm._view_ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.panaderosvm.services.DatabaseRepository

class DatabaseViewModel(app: Application) : AndroidViewModel(app)
{
    var mDatabaseRepository : DatabaseRepository? = null

    init {

        mDatabaseRepository = DatabaseRepository(app)
        //bussinesLinesList = mDatabaseRepository?.returnFunDBPrePopulate()
    }
}