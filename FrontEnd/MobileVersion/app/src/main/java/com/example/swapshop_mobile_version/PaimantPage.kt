package com.example.swapshop_mobile_version

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class PaimantPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paimant_page)
        getSupportActionBar()!!.setTitle("Checkout")
        val paimantButton = findViewById<Button>(R.id.Billing)
        val confirm = findViewById<CheckBox>(R.id.checkBox)
        val email = findViewById<EditText>(R.id.email).text.toString()
        val phone = findViewById<EditText>(R.id.numberPhone).text.toString()
        val numberCard = findViewById<EditText>(R.id.numberCard).text.toString()
        val billingAdress = findViewById<EditText>(R.id.billingAdress).text.toString()
            val extras = intent.extras
            val price = extras?.getString("price")
            paimantButton.text = "Pay $price DT"

            paimantButton.setOnClickListener {
                if (email.isEmpty() || phone.isEmpty() || numberCard.isEmpty() || billingAdress.isEmpty()) {
                    val snackbar = Snackbar.make(
                        findViewById<View>(android.R.id.content),
                        "Error! Please confirm your data",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                } else {
                if (confirm.isChecked) {
                    Toast.makeText(this, "Checkout successful", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please check the confirmation checkbox", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}