package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class addProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val productNameExtra = intent.getStringExtra("productName")
        val priceExtra = intent.getStringExtra("price")
        val descriptionEdit = intent.getStringExtra("description")
        val button = findViewById<Button>(R.id.addButton)
        val contentPageName = findViewById<TextView>(R.id.textView)
        val productNameEditText = findViewById<EditText>(R.id.name)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
       // val indexProduct = intent.getStringExtra("index")
        if (productNameExtra != null && priceExtra != null) {
            productNameEditText.setText(productNameExtra)
            priceEditText.setText(priceExtra)
            descriptionEditText.setText(descriptionEdit)
            contentPageName.setText("Edit Product Informations")
            button.setText("Edit Informations")
        }
    }
    fun saveProduct(view: View) {
        var productName = findViewById<EditText>(R.id.name)
        var description = findViewById<EditText>(R.id.descriptionEditText)
        var price = findViewById<EditText>(R.id.priceEditText)
        if (productName.text.toString().isEmpty() || description.text.toString().isEmpty() ||
            price.text.toString().isEmpty()) {
            val snackbar = Snackbar.make(view, "Error! please confirm your data", Snackbar.LENGTH_LONG)
            snackbar.show()
        } else {
            if (productName.text.toString().length > 16){
                val snackbar = Snackbar.make(view, "Error! please confirm your product name", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
            val indexProduct = intent.getStringExtra("index")
            val productList = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("productName", productName.text.toString())
            bundle.putString("description", description.text.toString())
            bundle.putString("price", price.text.toString())
            bundle.putString("index", indexProduct.toString())
            productList.putExtras(bundle)
            startActivity(productList)
        }
    }
}