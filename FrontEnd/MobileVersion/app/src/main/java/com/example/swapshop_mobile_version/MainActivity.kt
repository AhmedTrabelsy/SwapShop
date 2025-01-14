package com.example.swapshop_mobile_version

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.example.swapshop_mobile_version.databinding.ActivityMainBinding
import com.example.swapshop_mobile_version.models.Products
import com.example.swapshop_mobile_version.models.WishItems
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val extrasPaimant = intent.extras
        if (extrasPaimant == null) {
            navigationBetweenFragments(HomeFragment())
            productsList = ArrayList<Products>()
        }
        //var filterList = ArrayList<Products>()
        //filterList.addAll
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        //bottomNavigationView.menu.getItem(2).isEnabled = false
        if (extrasPaimant != null) {
            val id = extrasPaimant?.getString("id")
            val productName = extrasPaimant?.getString("productName")
            val orderDate = extrasPaimant?.getString("orderDate")
            val billingAdress = extrasPaimant?.getString("billingAdress")
            val yeaforMap = extrasPaimant?.getString("isCallingToMap")
            if (yeaforMap != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, MapsFragment())
                    .commit()
            }
            val fragment = OrderFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            bundle.putString("productName", productName)
            bundle.putString("orderDate", orderDate)
            bundle.putString("billingAdress", billingAdress)

            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .commit()
        }

       /* val extras = intent.extras
        val callMap = extras?.getString("isCallingToMap")
        if (callMap != null){
            val fragment = MapsFragment()
            navigationBetweenFragments(fragment)
        }*/

        setupAddNewProductButton()

        binding.bottomNavigationView.setOnItemSelectedListener {
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
                SharedPreference(this).clearSharedPreference()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
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