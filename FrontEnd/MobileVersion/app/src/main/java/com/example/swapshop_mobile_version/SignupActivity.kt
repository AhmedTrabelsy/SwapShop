package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var username: TextView
    private lateinit var phone: TextView
    private lateinit var password: TextView
    private lateinit var signupbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = "Signup"


        firstName = findViewById(R.id.SettingsfirstName)
        lastName = findViewById(R.id.SettingslastName)
        email = findViewById(R.id.SettingsfirstName)
        username = findViewById(R.id.username)
        phone = findViewById(R.id.phoneNumber)
        password = findViewById(R.id.password)
        signupbtn = findViewById(R.id.register)

        signupbtn.setOnClickListener {
            if(firstName.text.toString() == "" ||
                lastName.text.toString() == "" ||
                email.text.toString() == "" ||
                phone.text.toString() == "" ||
                password.text.toString() == ""
                    ) {
                Toast.makeText(this@SignupActivity, "Please enter valid data", Toast.LENGTH_LONG).show()
            }else {
                signup()
            }
        }
    }

    private fun signup() {
        val getUserData = RetrofitClient.getRetrofitInstance().create(GetUserData::class.java)
        val call: Call<SignUpResponse> = getUserData.Signup(SignupData(username = username.text.toString(),
            firstName=firstName.text.toString(),
            lastName=lastName.text.toString(),
            email=email.text.toString(),
            phoneNumber=phone.text.toString(),
            password=password.text.toString(),
            ))
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                Log.d("responseLogin","${response}")
                if (response.isSuccessful) {
                    val intent = Intent(this@SignupActivity, Login::class.java)
                    startActivity(intent)
                    finish()
                    } else {
                    Toast.makeText(this@SignupActivity, "Error: Wrong Data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}