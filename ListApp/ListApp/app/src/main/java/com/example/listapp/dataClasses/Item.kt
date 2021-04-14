package com.example.listapp.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(var name: String? = null, var complete: Boolean): Parcelable