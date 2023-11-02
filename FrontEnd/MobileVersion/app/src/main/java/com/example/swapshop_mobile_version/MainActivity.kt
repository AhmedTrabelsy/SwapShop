package com.example.swapshop_mobile_version

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.databinding.ActivityMainBinding
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MainActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapter
    private lateinit var addNewProductButton: FloatingActionButton
    private lateinit var binding: ActivityMainBinding

    private var values = ArrayList<Products>()
    private var filteredValues = ArrayList<Products>()

    private fun setupAddNewProductButton() {
        addNewProductButton = findViewById(R.id.addNewProduct)
        addNewProductButton.setOnClickListener {
            val sharePage = Intent(this, addProduct::class.java)
            startActivity(sharePage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)
        myAdapter = MyAdapter(values)
        recyclerView = findViewById<RecyclerView>(R.id.productList).apply {
            layoutManager = manager
            adapter = myAdapter
            (myAdapter as MyAdapter).setOnItemClickListener(this@MainActivity)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

     /*   binding.search.isIconified = false

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })*/
        
        val pc = Categories("Pc's")
        val accessories = Categories("Accessories")
        values.add(Products("Pc Toshiba", "1500.0", "Pc cv", pc))
        values.add(Products("Pc Dell", "2500.0", "Pc cv", pc))
        values.add(Products("Pc Asus", "1400.0", "Pc cv", pc))
        values.add(Products("bicycle", "400.0", "Pc cv", pc))
        values.add(Products("headset", "350.0", "Pc cv", accessories))
        values.add(Products("mouse", "140.0", "Pc cv", accessories))
        values.add(Products("charger", "17.0", "Pc cv", accessories))
        values.add(Products("wired earphones", "401.0", "Pc cv", accessories))
        values.add(Products("wireless mouse", "802.0", "Pc cv", accessories))

        filteredValues.addAll(values) // Add all products to filteredValues initially

        val bundle = intent.extras
        val productName = bundle?.getString("productName")
        val productPrice = bundle?.getString("price")
        val productDescription = bundle?.getString("description")
        val category = bundle?.getString("category")
        val indexString = bundle?.getString("index")
        val index = indexString?.toIntOrNull()

        if (index != null) {
            if (productName != null && productPrice != null && productDescription != null && (index >= 0 && index < values.size)) {
                values[index].price = productPrice.toString()
                values[index].productName = productName
                values[index].description = productDescription
                values[index].category = Categories(category)
            }
        }
        if (productName != null && productPrice != null && productDescription != null && index == null) {
            values.add(Products(productName, productPrice, productDescription.toString(), Categories(category)))
        }
        setupAddNewProductButton()
    }

    fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Products>()
            if (query.isEmpty()) {
                filteredList.addAll(filteredValues) // When query is empty, show all products
            } else {
                val searchText = query.lowercase(Locale.ROOT)
                for (product in filteredValues) {
                    if (product.productName.lowercase(Locale.ROOT).contains(searchText)) {
                        filteredList.add(product)
                    }
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            }

            (myAdapter as MyAdapter).setFilteredList(filteredList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.settings -> {
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.logOut -> {
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int) {
        val selectedProduct = filteredValues[position]

        val intent = Intent(this, ProductsDetails::class.java)

        intent.putExtra("categoryName", selectedProduct.category.categoryName)
        intent.putExtra("productName", selectedProduct.productName)
        intent.putExtra("priceProduct", selectedProduct.price)
        intent.putExtra("description", selectedProduct.description)

        startActivity(intent)
    }
}
