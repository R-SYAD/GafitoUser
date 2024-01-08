package com.example.gafitouser.data

import android.os.Parcel
import android.os.Parcelable


data class LaporanData (
    val laporanId: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val laporanImage: String? = null,
    val merek: String? = null,
    val nomorPolisi: String? = null,
    val warna: String? = null,
    val time: Long? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(laporanId)
        parcel.writeString(userId)
        parcel.writeString(name)
        parcel.writeString(laporanImage)
        parcel.writeString(merek)
        parcel.writeString(nomorPolisi)
        parcel.writeString(warna)
        parcel.writeValue(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LaporanData> {
        override fun createFromParcel(parcel: Parcel): LaporanData {
            return LaporanData(parcel)
        }

        override fun newArray(size: Int): Array<LaporanData?> {
            return arrayOfNulls(size)
        }
    }
}