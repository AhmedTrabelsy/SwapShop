package com.example.swapshop_mobile_version.models
import android.os.Parcel
import android.os.Parcelable
import com.example.swapshop_mobile_version.models.Categories

data class Products(
    var id: Long, var productName: String?, var price: String?, var description: String?,
    var category: Categories, var picturePath: String?)
{}
