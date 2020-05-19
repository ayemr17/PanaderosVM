package com.example.panaderosvm.model.local.panaderias

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.panaderosvm.model.local.pueblos.PueblosEntity

@Dao
interface PanaderiasDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPanaderias(states : List<PanaderiasEntity>)

    @Query("SELECT * FROM _PANADERIAS")
    fun getAllPanaderias() : List<PanaderiasEntity>?

    @Query("SELECT * FROM _PANADERIAS WHERE ID = :id")
    fun getSinglePanaderiaWithId(id:Int): PanaderiasEntity?

    @Query("SELECT * FROM _PANADERIAS WHERE id_pueblo = :id_pueblo")
    fun getPanaderiasWithIdPueblo(id_pueblo :Int): List<PanaderiasEntity>?

}