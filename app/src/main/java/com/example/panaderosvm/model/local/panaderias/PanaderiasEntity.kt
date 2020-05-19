package com.example.panaderosvm.model.local.panaderias

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_PANADERIAS")
class PanaderiasEntity(
    @PrimaryKey(autoGenerate = true)
    var ID: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "direccion")
    var direccion: String,
    @ColumnInfo(name = "telefono")
    var telefono: String,
    @ColumnInfo(name = "id_empleador")
    var idEmpleador: Int,
    @ColumnInfo(name = "cantidad_empleados")
    var cantEmpleados: Int,
    @ColumnInfo(name = "id_pueblo")
    var idPueblo: Int,
    @ColumnInfo(name = "latlong")
    var latlong: String

) : Parcelable {

    override fun toString(): String {
        return nombre
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ID)
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(telefono)
        parcel.writeInt(idEmpleador)
        parcel.writeInt(cantEmpleados)
        parcel.writeInt(idPueblo)
        parcel.writeString(latlong)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PanaderiasEntity> {
        override fun createFromParcel(parcel: Parcel): PanaderiasEntity {
            return PanaderiasEntity(parcel)
        }

        override fun newArray(size: Int): Array<PanaderiasEntity?> {
            return arrayOfNulls(size)
        }
    }
}