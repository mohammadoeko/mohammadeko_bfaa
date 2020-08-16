package com.dicoding.picodiploma.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var isFavorite: String? = "0"
) : Parcelable