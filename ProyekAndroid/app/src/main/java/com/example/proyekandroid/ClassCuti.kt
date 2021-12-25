package com.example.proyekandroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClassCuti(
    var durasi: String,
    var keterangan: String,
    var start: String,
    var end: String,
    var username: String,
    var status: String
): Parcelable
