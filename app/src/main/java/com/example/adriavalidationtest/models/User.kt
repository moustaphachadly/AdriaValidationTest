package com.example.adriavalidationtest.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val uid: String, val username: String) : Parcelable {
    companion object {
        val USER_KEY = "USER_KEY"
    }

    constructor() : this("", "")
}