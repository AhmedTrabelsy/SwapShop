package com.example.swapshop_mobile_version

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProductsDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_details)


        val productName = intent.getStringExtra("productName")
        val categoryName = intent.getStringExtra("categoryName")
        val priceString = intent.getStringExtra("priceProduct")
        val description = intent.getStringExtra("description")

        val productTextView = findViewById<TextView>(R.id.productsName)
        val categoryTextView = findViewById<TextView>(R.id.category)
        val priceTextView = findViewById<TextView>(R.id.priceSelectedProducts)
        val descriptionTextView = findViewById<TextView>(R.id.Description)

        productTextView.text = "Name: "+productName
        categoryTextView.text = "Category: "+categoryName

        if (priceString != null) {
            val price = priceString.toDouble()
            val formattedPrice = String.format("%.2f DT", price)
            priceTextView.text = formattedPrice
        }

        descriptionTextView.text = "Decription: " + description
    }
}
