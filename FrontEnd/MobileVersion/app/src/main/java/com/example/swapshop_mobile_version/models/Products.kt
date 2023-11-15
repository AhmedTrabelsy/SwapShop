package com.example.swapshop_mobile_version.models

data class Products(var id: Long ,var productName: String, var price: String, var description: String,
                    var category: Categories, var picturePath: String){
}