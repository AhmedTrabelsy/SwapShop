package com.example.swapshop_mobile_version

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
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
    private lateinit var imageUrl: String

    private var values = ArrayList<Products>()
    private var imageUrls :String = "@drawable/shopping_cart_833314.png"

    private fun setupAddNewProductButton() {
        addNewProductButton = findViewById(R.id.addNewProduct)
        addNewProductButton.setOnClickListener {
            val sharePage = Intent(this, addProduct::class.java)
            startActivity(sharePage)
            overridePendingTransition(R.anim.in_right_anim, R.anim.out_left_anim)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()!!.setTitle("Products List")
        manager = LinearLayoutManager(this)
        myAdapter = MyAdapter(values, this)
        recyclerView = findViewById<RecyclerView>(R.id.productList).apply {
            layoutManager = manager
            adapter = myAdapter
            (myAdapter as MyAdapter).setOnItemClickListener(this@MainActivity)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        val pc = Categories(60,"Pc's")
        val accessories = Categories(80,"Accessories")
        values.add(Products(5,"Pc Toshiba", "1500.0", "Pc cv", pc, imageUrls))
        requestQueue = Volley.newRequestQueue(this)
        jsonParse()
        val bundle = intent.extras
        val productName = bundle?.getString("productName")
        val productPrice = bundle?.getString("price")
        val productId = bundle?.getLong("id")
        val productDescription = bundle?.getString("description")
        val category = bundle?.getString("category")
        val catId = bundle?.getLong("catId")
        val indexString = bundle?.getString("index")
        val index = indexString?.toIntOrNull()

        if (index != null) {
            if (catId != null && productName != null && productPrice != null && productDescription != null && (index >= 0 && index < values.size)) {
                if (productId != null) {
                    values[index].id = productId
                }
                values[index].price = productPrice.toString()
                values[index].productName = productName
                values[index].description = productDescription
                values[index].category = Categories(catId,category)
            }
        }
        if (catId != null && productId !=null && productName != null && productPrice != null && productDescription != null && index == null) {
            values.add(Products(productId,productName, productPrice, productDescription.toString(), Categories(catId,category), imageUrls))
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
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                for (i in 0 until response.length()) {
                    val product = response.getJSONObject(i)
                    val id = product.getLong("id")
                    val productName = product.getString("name")
                    val price = product.getDouble("price")
                    val description = product.getString("description")
                    val category = product.getJSONObject("category")
                    val categoryName = category.getString("name")
                    val catId = category.getLong("id")
                    val imagesArray = product.getJSONArray("images")
                    var imageUrl = "@drawable/app_background.png"
                    if (imagesArray.length() > 0) {
                        val firstImage = imagesArray.getJSONObject(0)
                        val imageName = firstImage.getString("name")
                        imageUrl = imageName
                        Log.d("uri's","$imageUrl")
                    } else {
                        Log.e("MainActivity", "JSONArray is empty")
                    }

                    val newUser = Products(id,productName, price.toString(), description, Categories(catId,categoryName), imageUrl)
                    values.add(newUser)
                }
                myAdapter.notifyDataSetChanged()
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
        intent.putExtra("imagePath",selectedProduct.picturePath)
        startActivity(intent)
        overridePendingTransition()
    }

    private fun overridePendingTransition() {
        overridePendingTransition(R.anim.in_right_anim, R.anim.out_left_anim)
    }

}