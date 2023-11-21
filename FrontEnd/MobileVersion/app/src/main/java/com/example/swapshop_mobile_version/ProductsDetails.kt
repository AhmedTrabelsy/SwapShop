package com.example.swapshop_mobile_version

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ProductsDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val productName = intent.getStringExtra("productName")
        val categoryName = intent.getStringExtra("categoryName")
        val priceString = intent.getStringExtra("priceProduct")
        val description = intent.getStringExtra("description")
        var created_on = intent.getStringExtra("created_on")
        var updated_on = intent.getStringExtra("updated_on")
        var listImages = intent.getStringArrayListExtra("imageList")

        val imagePath = intent.getStringExtra("imagePath")
        val imageUrl1 = if (!listImages.isNullOrEmpty() && listImages.size > 0) {
            "http://34.199.239.78:8888/PRODUCT-SERVICE/${listImages[0]}"
        }else{
            "http://34.199.239.78:8888/PRODUCT-SERVICE/${imagePath}"
        }
        val imageUrl2 = if (!listImages.isNullOrEmpty() && listImages.size > 1) {
            "http://34.199.239.78:8888/PRODUCT-SERVICE/${listImages[1]}"
        } else {
            "http://34.199.239.78:8888/PRODUCT-SERVICE/${imagePath}"
        }
        val imageUrl3 = if (!listImages.isNullOrEmpty() && listImages.size > 2) {
            "http://34.199.239.78:8888/PRODUCT-SERVICE/${listImages[2]}"
        } else {
            "http://34.199.239.78:8888/PRODUCT-SERVICE/${imagePath}"
        }
        getSupportActionBar()!!.title = productName
        val productTextView = findViewById<TextView>(R.id.productsName)
        val categoryTextView = findViewById<TextView>(R.id.category)
        val priceTextView = findViewById<TextView>(R.id.priceSelectedProducts)
        val descriptionTextView = findViewById<TextView>(R.id.Description)
        var picture = findViewById<ImageView>(R.id.productImage)
        var picture2 = findViewById<ImageView>(R.id.productImage2)
        var picture3 = findViewById<ImageView>(R.id.productImage3)
        Picasso.get().load(imageUrl1).into(picture)
        Picasso.get().load(imageUrl2).into(picture2)
        Picasso.get().load(imageUrl3).into(picture3)

        productTextView.text = productName
        categoryTextView.text = "Category: "+categoryName

        if (priceString != null) {
            val price = priceString.toDouble()
            val formattedPrice = String.format("%.2f DT", price)
            priceTextView.text = formattedPrice
        }

        descriptionTextView.text = description
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.in_left_anim, R.anim.out_right_anim)
    }
}
