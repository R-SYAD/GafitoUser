package com.example.gafitouser.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaporanData (
    val laporanId: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val laporanImage: String? = null,
    val merek: String? = null,
    val nomorPolisi: String? = null,
    val warna: String? = null,
    val description: String? = null,
    val time: Long? = null
): Parcelable