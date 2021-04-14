package com.example.listapp.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class List(var title: String? = null, var uuid: String? = null):Parcelable
