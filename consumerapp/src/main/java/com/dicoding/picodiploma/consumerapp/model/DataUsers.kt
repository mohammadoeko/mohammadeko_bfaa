package com.dicoding.picodiploma.consumerapp.model

import android.os.Parcel
import android.os.Parcelable

data class DataUsers(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: Int = 0,
    var followers: Int = 0,
    var following: Int = 0,
    var isFavorite: String? = "0"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(avatar)
        parcel.writeString(company)
        parcel.writeString(location)
        parcel.writeInt(repository)
        parcel.writeInt(followers)
        parcel.writeInt(following)
        parcel.writeString(isFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataUsers> {
        override fun createFromParcel(parcel: Parcel): DataUsers {
            return DataUsers(parcel)
        }

        override fun newArray(size: Int): Array<DataUsers?> {
            return arrayOfNulls(size)
        }
    }
}