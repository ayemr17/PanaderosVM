package com.example.panaderosvm.model.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.panaderosvm.model.local.panaderias.PanaderiasDAO
import com.example.panaderosvm.model.local.panaderias.PanaderiasEntity
import com.example.panaderosvm.model.local.pueblos.PueblosDAO
import com.example.panaderosvm.model.local.pueblos.PueblosEntity


@Database(
    entities = [
    PueblosEntity::class,
    PanaderiasEntity::class

    ], version = 4
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun pueblosDao() : PueblosDAO
    abstract fun panaderiasDao() : PanaderiasDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also {
                        INSTANCE = it
                    }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "sindicatovm.db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("ONCREATE", "Database has been created.")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d("ONOPEN", "Database has been opened.")
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }
}