package com.example.panaderosvm.model.local.pueblos

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "_PUEBLOS")
data class PueblosEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var ID: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "departamento")
    var departamento: String,
    @ColumnInfo(name = "latlong")
    var latlong: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(ID)
        dest?.writeString(nombre)
        dest?.writeString(departamento)
        dest?.writeString(latlong)
    }

    override fun toString(): String {
        return nombre
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PueblosEntity> {
        override fun createFromParcel(parcel: Parcel): PueblosEntity {
            return PueblosEntity(parcel)
        }

        override fun newArray(size: Int): Array<PueblosEntity?> {
            return arrayOfNulls(size)
        }
    }

}