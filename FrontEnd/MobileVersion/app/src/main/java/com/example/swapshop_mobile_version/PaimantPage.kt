package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class PaimantPage : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paimant_page)
        getSupportActionBar()?.setTitle("Checkout")

        val paimantButton = findViewById<Button>(R.id.Billing)
        val confirm = findViewById<CheckBox>(R.id.checkBox)
        val extras = intent.extras
        val price = extras?.getString("price")
        val productName = extras?.getString("productName")
        paimantButton.text = "Pay $price DT"

        paimantButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val phone = findViewById<EditText>(R.id.numberPhone).text.toString()
            val numberCard = findViewById<EditText>(R.id.numberCard).text.toString()
            val billingAdress = findViewById<EditText>(R.id.billingAdress).text.toString()

            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = currentDateTime.format(formatter).toString()

            val uuid: String = UUID.randomUUID().toString()

            /*val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()

            bundle.putString("id", uuid)
            bundle.putString("productName", productName)
            bundle.putString("orderDate", date)
            bundle.putString("billingAdress", billingAdress)

            intent.putExtras(bundle)
            startActivity(intent)*/


           // Toast.makeText(this,"Order Passed Succesfully",Toast.LENGTH_LONG).show()

            if (email.isEmpty() || phone.isEmpty() || numberCard.isEmpty() || billingAdress.isEmpty()) {
                val snackbar = Snackbar.make(
                    findViewById<View>(android.R.id.content),
                    "Error! Please confirm your data",
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            } else {
                if (confirm.isChecked) {
                    requestQueue = Volley.newRequestQueue(this)
                    saveOrder(requestQueue!!)
                    //finish()
                } else {
                    Toast.makeText(this, "Please check the confirmation checkbox", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun saveOrder(requestQueue: RequestQueue) {
        val extras = intent?.extras
        val prodId = extras?.getLong("prodId", 0L)
        val billingAddress = findViewById<EditText>(R.id.billingAdress)?.text.toString()
        val userId: Long = 1


        val url = "http://34.199.239.78:8888/ORDER-SERVICE/neworder"

        val orderObject = JSONObject().apply {
            put("userId", userId)
            put("billingAdress", billingAddress)
            put("productId", prodId)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            orderObject,
            { response ->
                // Handle the response here
            },
            { error ->
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }


}