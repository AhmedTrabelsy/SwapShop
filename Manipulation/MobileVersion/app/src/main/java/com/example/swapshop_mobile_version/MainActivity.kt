package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.models.Products
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() , MyAdapter.OnItemClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var addNewProductButton: FloatingActionButton

    private fun setupAddNewProductButton() {
        addNewProductButton = findViewById(R.id.addNewProduct)
        addNewProductButton.setOnClickListener {
            val sharePage = Intent(this, addProduct::class.java)
            startActivity(sharePage)
        }
    }
    val values = arrayListOf<Products>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)
        myAdapter = MyAdapter(values)
        recyclerView = findViewById<RecyclerView>(R.id.productList).apply {
            layoutManager = manager
            adapter = myAdapter
        }
        values.add(Products("Pc Toshiba",1500.0,"Pc cv"))
        values.add(Products("Pc Dell",2500.0,"Pc cv"))
        values.add(Products("Pc Asus",1400.0,"Pc cv"))
        val bundle = intent.extras
        val productName = bundle?.getString("productName")
        val productPrice = bundle?.getString("price")?.toDouble()
        val productDescription = bundle?.getString("description")
        val indexString = bundle?.getString("index")
        val index = indexString?.toIntOrNull()

        if (index != null) {
            if (productName != null && productPrice != null && productDescription != null && (index >= 0 && index < values.size)) {
                //  if (index >= 0 && index < values.size) {
                values[index].price = productPrice
                values[index].productName = productName
                values[index].description = productDescription
                //  } else {

                //}
            }
        }
        if (productName != null && productPrice != null && productDescription != null && index == null) {
            values.add(Products(productName,productPrice, productDescription.toString()))
        }
        setupAddNewProductButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(this, "Profile sélectionnée", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.settings -> {
                Toast.makeText(this, "settings sélectionnée", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.logOut -> {
                Toast.makeText(this, "Logout sélectionnée", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

}
