package com.example.proyekandroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var email : String,
    var name : String,
    var password : String,
    var role : String,
    var telp : String
) : Parcelable
