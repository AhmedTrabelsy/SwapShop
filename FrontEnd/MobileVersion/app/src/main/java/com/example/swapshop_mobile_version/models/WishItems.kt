package com.example.swapshop_mobile_version.models

import android.os.Parcel
import android.os.Parcelable

class WishItems(val userId: Long?, val product: Products?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readParcelable(Products::class.java.classLoader)
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(userId)
        dest.writeParcelable(product, flags)
    }

    companion object CREATOR : Parcelable.Creator<WishItems> {
        override fun createFromParcel(parcel: Parcel): WishItems {
            return WishItems(parcel)
        }

        override fun newArray(size: Int): Array<WishItems?> {
            return arrayOfNulls(size)
        }
    }
}
