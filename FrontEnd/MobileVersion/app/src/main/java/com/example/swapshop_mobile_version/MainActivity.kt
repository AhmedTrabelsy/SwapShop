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
import androidx.fragment.app.Fragment
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

class MainActivity : AppCompatActivity() {
    private lateinit var addNewProductButton: FloatingActionButton
    private lateinit var binding: ActivityMainBinding
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
        //var filterList = ArrayList<Products>()
        //filterList.addAll
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        //bottomNavigationView.menu.getItem(2).isEnabled = false

        val fragment = ProductsFragment()
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
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .commit()


        getSupportActionBar()!!.setTitle("Products List")

        setupAddNewProductButton()

        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId){
                R.id.home -> navigationBetweenFragments(HomeFragment())
                R.id.products -> navigationBetweenFragments(ProductsFragment())
                else -> {

                }
            }
            true
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

    private fun overridePendingTransition() {
        overridePendingTransition(R.anim.in_right_anim, R.anim.out_left_anim)
    }

    private fun navigationBetweenFragments(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment,fragment)
        fragmentTransaction.commit()
    }

}