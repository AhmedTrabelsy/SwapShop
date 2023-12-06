package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Order
import com.example.swapshop_mobile_version.models.Products

private lateinit var recyclerView: RecyclerView
private lateinit var manager: RecyclerView.LayoutManager
private lateinit var myAdapter: MyOrderAdapter
private var values = ArrayList<Order>()
private var requestQueue: RequestQueue? = null
private var imagesArrays = ArrayList<String>()
class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        supportActionBar!!.setTitle("Order Page")
        /*values.add(Order(12L,"Bizerte","12-02-2023",
            Products(2L,"journal","1500","cv", Categories(5L,"PC"),"123.png")
        ))*/
        val extras = intent.extras
        val id = extras?.getString("id")
        val productName = extras?.getString("productName")
        val orderDate = extras?.getString("orderDate")
        val billingAdress = extras?.getString("billingAdress")
        if (id != null && billingAdress!= null && orderDate != null && productName != null) {
            values.add(Order(id, billingAdress, orderDate, productName))
        }

        recyclerView = findViewById(R.id.orders)
        manager = LinearLayoutManager(this)
        myAdapter = MyOrderAdapter(values, this)

        recyclerView.layoutManager = manager
        recyclerView.adapter = myAdapter

    }
}