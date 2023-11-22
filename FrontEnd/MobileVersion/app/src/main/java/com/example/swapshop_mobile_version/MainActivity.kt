package com.example.swapshop_mobile_version

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapter
    private lateinit var addNewProductButton: FloatingActionButton
    private lateinit var binding: ActivityMainBinding
    private var requestQueue: RequestQueue? = null
    private lateinit var imageUrl: String
    private var imagesArrays = ArrayList<String>()

    private var values = ArrayList<Products>()
    private var filterList = ArrayList<Products>()
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
        //var filterList = ArrayList<Products>()
        //filterList.addAll
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        //bottomNavigationView.menu.getItem(2).isEnabled = false

        getSupportActionBar()!!.setTitle("Products List")
        manager = LinearLayoutManager(this)
        myAdapter = MyAdapter(filterList, this)
        recyclerView = findViewById<RecyclerView>(R.id.productList).apply {
            layoutManager = manager
            adapter = myAdapter
            (myAdapter as MyAdapter).setOnItemClickListener(this@MainActivity)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)



        val pc = Categories(60, "Pc's")
        val accessories = Categories(80, "Accessories")
        values.add(Products(5, "Pc Toshiba", "1500.0", "Pc cv", pc, imageUrls))
        requestQueue = Volley.newRequestQueue(this)
        jsonParse()
        filterList.addAll(values)
        val searchItem = findViewById<SearchView>(R.id.searchBar)
        val editText = searchItem.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList.clear()
                editText.setTextColor(Color.BLACK);
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    values.forEach {
                        if (it.productName.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            filterList.add(it)
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                } else {
                    filterList.addAll(values)
                }
                //myAdapter.submitList(filterList)
                return false
            }

        })


        val bundle = intent.extras
        val productName = bundle?.getString("productName")
        val productPrice = bundle?.getString("priceProduct")
        val productId = bundle?.getLong("id")
        val productDescription = bundle?.getString("description")
        val category = bundle?.getString("categoryName")
        val catId = bundle?.getLong("idCat")
        val image = bundle?.getString("imagePath")
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
                values[index].category = Categories(catId, category)
                if (image != null) {
                    values[index].picturePath = image
                }
                //filterList.addAll(values)
            }
        } else {
           /* if (catId != null && productId != null && productName != null && productPrice != null && productDescription != null && index == null && image != null) {
                values.add(
                    Products(
                        productId,
                        productName,
                        productPrice,
                        productDescription.toString(),
                        Categories(catId, category),
                        image
                    )
                )
                filterList.addAll(values)
            }*/
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
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

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
                    for (i in 0 until imagesArray.length()) {
                        val imageUrl = imagesArray.getString(i)
                        imagesArrays.add(imageUrl)
                    }
                    var imageUrl = "@drawable/app_background.png"
                    if (imagesArray.length() > 0) {
                        val firstImage = imagesArray.getJSONObject(0)
                        val imageName = firstImage.getString("name")
                        imageUrl = imageName
                        Log.d("uri's", "$imageUrl")
                    } else {
                        Log.e("MainActivity", "JSONArray is empty")
                    }

                        val newUser = Products(
                            id,
                            productName,
                            price.toString(),
                            description,
                            Categories(catId, categoryName),
                            imageUrl,
                        )
                        values.add(newUser)
                        filterList.add(newUser)
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
        intent.putStringArrayListExtra("imageList", imagesArrays)

        startActivity(intent)
        overridePendingTransition()
    }

    private fun overridePendingTransition() {
        overridePendingTransition(R.anim.in_right_anim, R.anim.out_left_anim)
    }

}