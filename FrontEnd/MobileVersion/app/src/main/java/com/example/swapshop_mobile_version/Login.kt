package com.example.swapshop_mobile_version

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var mail: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Login"

        mail = findViewById(R.id.loginEmail)
        password = findViewById(R.id.loginPassword)
        login = findViewById(R.id.signInBtn)

        login.setOnClickListener {
            getAccessToken()
        }
    }
    private fun getAccessToken() {
        val getUserData = RetrofitClient.getRetrofitInstance().create(GetUserData::class.java)
        val passwordString = password.text.toString()
        val username = mail.text.toString()

        val call: Call<AccessToken> = getUserData.getAccessToken(LoginData(username = username, password = passwordString))
        Log.d("login","$call")
        call.enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                Log.d("responseLogin","${response}")
                if (response.isSuccessful) {
                   // val accessToken: AccessToken? = response.body()
                    //if (accessToken != null) {
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Optional, to finish the current activity
                    //}  else {
                        //Toast.makeText(this@Login, "Error: Access token is null", Toast.LENGTH_LONG).show()
                    //}
                } else {
                    Toast.makeText(this@Login, "Error: Wrong credentials", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Toast.makeText(this@Login, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
