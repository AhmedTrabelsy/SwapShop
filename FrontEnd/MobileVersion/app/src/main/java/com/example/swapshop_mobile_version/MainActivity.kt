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

        val extras = intent.extras
        val callMap = extras?.getString("isCallingToMap")
        if (callMap != null){
            val fragment = MapsFragment()
            navigationBetweenFragments(fragment)
        }

        setupAddNewProductButton()

        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId){
                R.id.home -> {
                    val fragmentHome = HomeFragment()
                    navigationBetweenFragments(fragmentHome)
                }
                R.id.products -> {
                    val fragment = ProductsFragment()
                    navigationBetweenFragments(fragment)
                }
                R.id.wishlist -> {
                    val fragment = WhishlistFragment()
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

    public fun navigationBetweenFragments(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment)
        fragmentTransaction.commit()
    }

}