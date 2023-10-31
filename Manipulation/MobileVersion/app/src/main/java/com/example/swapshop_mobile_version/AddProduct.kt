package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.swapshop_mobile_version.models.Categories
import com.google.android.material.snackbar.Snackbar

class addProduct : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val categories = ArrayList<Categories>()

        val pc = Categories("Pc's")
        val accessories = Categories("Accessoires")
        categories.add(pc)
        categories.add(accessories)

        val nameCategories = ArrayList<String>()
        nameCategories.add("Select a category")
        for (category in categories) {
            category.categoryName?.let { nameCategories.add(it) }
        }

        val classeSpinner = findViewById<Spinner>(R.id.categories)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classeSpinner.adapter = adapter
        classeSpinner.onItemSelectedListener = this
        classeSpinner.setSelection(0)

        val productNameExtra = intent.getStringExtra("productName")
        val priceExtra = intent.getStringExtra("price")
        val descriptionEdit = intent.getStringExtra("description")
        val category = intent.getStringExtra("category")

        val button = findViewById<Button>(R.id.addButton)
        val contentPageName = findViewById<TextView>(R.id.textView)
        val productNameEditText = findViewById<EditText>(R.id.name)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        val categorySpinner = findViewById<Spinner>(R.id.categories)

        if (productNameExtra != null && priceExtra != null) {
            productNameEditText.setText(productNameExtra)
            priceEditText.setText(priceExtra)
            descriptionEditText.setText(descriptionEdit)

            var index = 0
            for (i in 0 until nameCategories.size) {
                if (nameCategories[i] == category) {
                    index = i
                    break
                }
            }
            categorySpinner.setSelection(index)

            contentPageName.setText("Edit Product Informations")
            button.setText("Edit Informations")
        }
    }

    fun saveProduct(view: View) {
        var productName = findViewById<EditText>(R.id.name)
        var description = findViewById<EditText>(R.id.descriptionEditText)
        var price = findViewById<EditText>(R.id.priceEditText)
        var category = findViewById<Spinner>(R.id.categories)

        if (productName.text.toString().isEmpty() || description.text.toString().isEmpty() || price.text.toString().isEmpty()
            || category.selectedItem.toString() == "Select a category") {
            val snackbar = Snackbar.make(view, "Error! please confirm your data", Snackbar.LENGTH_LONG)
            snackbar.show()
        } else {
            if (productName.text.toString().length > 16) {
                val snackbar = Snackbar.make(view, "Error! please confirm your product name", Snackbar.LENGTH_LONG)
                snackbar.show()
            }

            val indexProduct = intent.getStringExtra("index")
            val productList = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("productName", productName.text.toString())
            bundle.putString("description", description.text.toString())
            bundle.putString("price", price.text.toString())
            bundle.putString("category", category.selectedItem.toString())
            bundle.putString("index", indexProduct.toString())
            productList.putExtras(bundle)
            startActivity(productList)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position > 0) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
