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
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.databinding.ActivityMainBinding
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

class MainActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapter
    private lateinit var addNewProductButton: FloatingActionButton
    private lateinit var binding: ActivityMainBinding
    private var requestQueue: RequestQueue? = null

    private var values = ArrayList<Products>()

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
        getSupportActionBar()!!.setTitle("Products List")
        manager = LinearLayoutManager(this)
        myAdapter = MyAdapter(values)
        recyclerView = findViewById<RecyclerView>(R.id.productList).apply {
            layoutManager = manager
            adapter = myAdapter
            (myAdapter as MyAdapter).setOnItemClickListener(this@MainActivity)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        val pc = Categories("Pc's")
        val accessories = Categories("Accessories")
        values.add(Products("Pc Toshiba", "1500.0", "Pc cv", pc))
        requestQueue = Volley.newRequestQueue(this)
        jsonParse()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    private fun jsonParse() {
        val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                if (response.has("_embedded")) {
                    val embedded = response.getJSONObject("_embedded")
                    val productsArray = embedded.getJSONArray("products")

                    for (i in 0 until productsArray.length()) {
                        val product = productsArray.getJSONObject(i)
                        val productName = product.getString("name")
                        val price = product.getDouble("price")
                        val description = product.getString("description")
                        val pc = Categories("Pc's")
                        val newUser = Products(productName, price.toString(), description, pc)
                        values.add(newUser)
                    }
                    myAdapter.notifyDataSetChanged()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })

        requestQueue?.add(request)
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
        val selectedProduct = values[position]

        val intent = Intent(this, ProductsDetails::class.java)

        intent.putExtra("categoryName", selectedProduct.category.categoryName)
        intent.putExtra("productName", selectedProduct.productName)
        intent.putExtra("priceProduct", selectedProduct.price)
        intent.putExtra("description", selectedProduct.description)

        startActivity(intent)
    }
}