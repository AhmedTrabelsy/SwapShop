package com.example.swapshop_mobile_version

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.databinding.ActivityMainBinding
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.example.swapshop_mobile_version.models.WishItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var addNewProductButton: FloatingActionButton
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapter
    private var requestQueue: RequestQueue? = null
    private var imagesArrays = ArrayList<String>()
    private var productsList = ArrayList<Products>()
    private var whishItemsList = ArrayList<WishItems>()
    //rafresh swipe
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigationBetweenFragments(HomeFragment())
        productsList = ArrayList<Products>()
        //var filterList = ArrayList<Products>()
        //filterList.addAll
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        //bottomNavigationView.menu.getItem(2).isEnabled = false
        requestQueue = Volley.newRequestQueue(this)
        jsonParse()

        setupAddNewProductButton()

        requestQueue = Volley.newRequestQueue(this)
        jsonParse()
        val bundleProducts = Bundle()
        bundleProducts.putParcelableArrayList("productsList", productsList)
        jsonParseWishItems()

        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId){
                R.id.home -> {
                    val fragmentHome = HomeFragment()
                    fragmentHome.arguments = bundleProducts
                    navigationBetweenFragments(fragmentHome)
                }
                R.id.products -> {
                    val fragment = ProductsFragment()
                    fragment.arguments = bundleProducts
                    navigationBetweenFragments(fragment)
                }
                R.id.wishlist -> {
                    val fragment = WhishlistFragment()
                    val bundle = Bundle()
                    Log.d("wihshedItems","${whishItemsList}")
                    val wishItemsParcelableList = ArrayList<Parcelable>(whishItemsList.map { it as Parcelable })
                    bundle.putParcelableArrayList("wishItems", wishItemsParcelableList)
                    Log.d("bundle Wished","${bundle}")
                    fragment.arguments = bundle
                    navigationBetweenFragments(fragment)
                }
                R.id.dashboard -> {
                    val fragment = DashboardFragment()
                    navigationBetweenFragments(fragment)
                }
                else -> {

                }
            }
            true
        }
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
                    productsList.add(newUser)

                    /* values.add(newUser)
                     filterList.add(newUser)*/

                }
               // Log.d("from main","$newUser")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    private fun jsonParseWishItems() {
        val url = "http://34.199.239.78:8888/WISHLIST-SERVICE/1"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val userProducts = response.getJSONArray("products")

                for (i in 0 until userProducts.length()) {
                    val product = userProducts.optJSONObject(i)

                    if (product != null) {
                        // Parsing for non-null product object
                        val userId = response.optLong("user_id")
                        val productId = product.optLong("id")
                        val productName = product.optString("name")
                        val price = product.optDouble("price")
                        val description = product.optString("description")

                        val category = product.optJSONObject("category")
                        val categoryId = category?.optLong("id") ?: 0
                        val categoryName = category?.optString("name") ?: "Unknown"

                        val imagesArray = product.optJSONArray("images")
                        val imagesUrls = ArrayList<String>()

                        imagesArray?.let {
                            for (j in 0 until it.length()) {
                                val imageObject = it.optJSONObject(j)
                                val imageName = imageObject?.optString("name")
                                imageName?.let {
                                    val fullImageUrl = "$it"
                                    imagesUrls.add(fullImageUrl)
                                }
                            }
                        }

                        val imageUrl = if (imagesUrls.isNotEmpty()) imagesUrls[0] else "@drawable/app_background.png"

                        val newUser = WishItems(
                            userId,
                            Products(
                                productId,
                                productName,
                                price.toString(),
                                description,
                                Categories(categoryId, categoryName),
                                imageUrl
                            )
                        )
                        whishItemsList.add(newUser)
                        Log.d("widhedItems", "${whishItemsList[0].userId}")
                    }
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })

        requestQueue?.add(request)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                    val fragment = ProfileFragment()
                    navigationBetweenFragments(fragment)
                true
            }
            R.id.settings -> {
                val fragment = SettingsFragment()
                navigationBetweenFragments(fragment)
                true
            }
            R.id.logOut -> {
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun overridePendingTransition() {
        overridePendingTransition(R.anim.in_right_anim, R.anim.out_left_anim)
    }

    private fun navigationBetweenFragments(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment)
        fragmentTransaction.commit()
    }

}