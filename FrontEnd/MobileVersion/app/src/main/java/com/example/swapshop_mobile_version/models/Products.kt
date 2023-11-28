package com.example.swapshop_mobile_version.models
import android.os.Parcel
import android.os.Parcelable
import com.example.swapshop_mobile_version.models.Categories

data class Products(
    var id: Long, var productName: String?, var price: String?, var description: String?,
    var category: Categories, var picturePath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Categories::class.java.classLoader)!!,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(productName)
        parcel.writeString(price)
        parcel.writeString(description)
        parcel.writeParcelable(category, flags) // Write Categories object
        parcel.writeString(picturePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Products> {
        override fun createFromParcel(parcel: Parcel): Products {
            return Products(parcel)
        }

        override fun newArray(size: Int): Array<Products?> {
            return arrayOfNulls(size)
        }
    }
}
