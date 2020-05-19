package com.example.panaderosvm.model.local.pueblos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PueblosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPueblos(states : List<PueblosEntity>)

    @Query("SELECT * FROM _PUEBLOS")
    fun getAllPueblos() : List<PueblosEntity>

    @Query("SELECT * FROM _PUEBLOS WHERE ID = :id")
    fun getSinglePuebloWithId(id:Int): PueblosEntity?

    @Query("SELECT * FROM _PUEBLOS WHERE nombre = :nombre")
    fun getSinglePuebloWithNombre(nombre:String): PueblosEntity?

}