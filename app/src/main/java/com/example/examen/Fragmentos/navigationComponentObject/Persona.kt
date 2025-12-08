package com.example.examen.Fragmentos.navigationComponentObject

import android.os.Parcel
import android.os.Parcelable


data class Persona(

    var nombre :String,
    var apellido : String,
    var numero : Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeInt(numero)
    }
    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Persona> {
        override fun createFromParcel(parcel: Parcel): Persona = Persona(parcel)
        override fun newArray(size: Int): Array<Persona?> = arrayOfNulls(size)
    }
}