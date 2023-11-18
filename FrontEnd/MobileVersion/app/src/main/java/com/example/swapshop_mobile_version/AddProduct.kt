package com.example.swapshop_mobile_version

import android.app.Activity
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

class addProduct : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val nameCategories = ArrayList<String>()
    private var requestQueue: RequestQueue? = null
    private var requestQueueUpdate: RequestQueue? = null
    private var categories = ArrayList<Categories>()
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private var imageArray: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        supportActionBar!!.setTitle("Add Product")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       // requestQueue = Volley.newRequestQueue(this@addProduct)
        var button = findViewById<Button>(R.id.addButton)
        val addImageButton = findViewById<Button>(R.id.addImage)
        addImageButton.setOnClickListener {
            requestStoragePermission()
        }

        val categories = ArrayList<Categories>()

        val pc = Categories(60,"Pc's")
        val accessories = Categories(80,"Accessoires")
        categories.add(pc)
        categories.add(accessories)

        nameCategories.add("Select a category")
        jsonParse()
        for (category in categories) {
            category.categoryName?.let { nameCategories.add(it) }
        }

        val classeSpinner = findViewById<Spinner>(R.id.categories)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classeSpinner.adapter = adapter
        classeSpinner.onItemSelectedListener = this
        classeSpinner.setSelection(0)

        val productNameExtra = intent.getStringExtra("productName")
        val priceExtra = intent.getStringExtra("price")
        val descriptionEdit = intent.getStringExtra("description")
        val category = intent.getStringExtra("category")
        val id = intent.getLongExtra("idProd",0L)
        val picturePath = intent.getStringExtra("picPath")
        val idCat = intent.getLongExtra("catId",0L)
        val pos = intent.getStringExtra("index")

            if (productNameExtra == null && priceExtra == null) {
                button.setOnClickListener {
                    //requestQueue = Volley.newRequestQueue(this)
                    saveProduct()
                }
            }

        val contentPageName = findViewById<TextView>(R.id.textView)
        val productNameEditText = findViewById<EditText>(R.id.name)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        val categorySpinner = findViewById<Spinner>(R.id.categories)
        val picture = findViewById<ImageView>(R.id.picture)

        if (productNameExtra != null && priceExtra != null) {
            productNameEditText.setText(productNameExtra)
            priceEditText.setText(priceExtra)
            descriptionEditText.setText(descriptionEdit)
            val imageUrl = "http://34.199.239.78:8888/PRODUCT-SERVICE/${picturePath}"
            //Log.d("MyAdapter", "Image URL: $imageUrl")
            Picasso.get().load(imageUrl).into(picture)
            if (category != null) {
                val index = nameCategories.indexOf(category)
                if (index != -1) {
                    categorySpinner.setSelection(index)
                }
            }

            button.setOnClickListener {
                //requestQueue = Volley.newRequestQueue(this)
                //saveProduct()
                val productName = findViewById<EditText>(R.id.name).text.toString()
                val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
                val price = findViewById<EditText>(R.id.priceEditText).text.toString()
                val category = findViewById<Spinner>(R.id.categories).selectedItem.toString()
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("categoryName", category)
                intent.putExtra("productName", productName)
                intent.putExtra("priceProduct", price)
                intent.putExtra("description", description)
                intent.putExtra("imagePath",picturePath)
                intent.putExtra("id",id)
                intent.putExtra("idCat",idCat)
                intent.putExtra("index",pos)
                startActivity(intent)
            }


           /* var index = 0
            for (i in 0 until nameCategories.size) {
                if (nameCategories[i] == category) {
                    index = i
                    break
                }
            }*/
            //categorySpinner.setSelection(index)

            contentPageName.text = "EDIT PRODUCT"
            button.text = "Edit Information"
            supportActionBar!!.title = "Edit Product"
        }
    }
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PICK_IMAGE_REQUEST
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PICK_IMAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                val imageView = findViewById<ImageView>(R.id.picture)
                imageView.setImageBitmap(bitmap)

                val imageData = getImageData()
                imageArray.add(Base64.encodeToString(imageData, Base64.DEFAULT))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun getImageData(): ByteArray {
        val inputStream = contentResolver.openInputStream(imageUri!!)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    private fun jsonParse() {
        val url = "http://34.199.239.78:8888/CATEGORY-SERVICE/categories"
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                categories.clear()
                nameCategories.clear()
                nameCategories.add("Select a category")

                for (i in 0 until response.length()) {
                    val categoriesRequest = response.getJSONObject(i)
                    val categoryId = categoriesRequest.getLong("id")
                    val categoryName = categoriesRequest.getString("name")
                    nameCategories.add(categoryName)
                    categories.add(Categories(categoryId, categoryName))
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameCategories)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val classeSpinner = findViewById<Spinner>(R.id.categories)
                classeSpinner.adapter = adapter
                adapter.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue = Volley.newRequestQueue(this)
        requestQueue?.add(request)
    }

    private fun saveProduct() {
        val productName = findViewById<EditText>(R.id.name).text.toString()
        val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
        val price = findViewById<EditText>(R.id.priceEditText).text.toString()
        val category = findViewById<Spinner>(R.id.categories).selectedItem.toString()

        if (productName.isEmpty() || description.isEmpty() || price.isEmpty() || category == "Select a category") {
            val snackbar = Snackbar.make(
                findViewById<View>(android.R.id.content),
                "Error! Please confirm your data",
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        } else {
            try {
                val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products"
                val request = object : VolleyFormData(
                    Request.Method.POST,
                    url,
                    Response.Listener { response ->
                        val productListIntent = Intent(this, MainActivity::class.java)
                        startActivity(productListIntent)
                        overridePendingTransition(R.anim.in_left_anim, R.anim.out_right_anim)
                    },
                    Response.ErrorListener { error ->
                        val snackbar = Snackbar.make(
                            findViewById<View>(android.R.id.content),
                            "Error adding product: ${error.message}",
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.show()
                    }) {

                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["name"] = productName
                        params["description"] = description
                        params["price"] = price.toString()
                        val categoryId = getCategoryIDByName(category)
                        params["categoryID"] = categoryId.toString()
                        return params
                    }

                    override fun getByteData(): Map<String, ByteArray>? {
                        val params = HashMap<String, ByteArray>()

                        for ((index, imageData) in imageArray.withIndex()) {
                            params["images[$index]"] = Base64.decode(imageData, Base64.DEFAULT)
                        }

                        return params
                    }
                }

                requestQueue?.add(request)

                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)

            } catch (e: NumberFormatException) {
                val snackbar = Snackbar.make(
                    findViewById<View>(android.R.id.content),
                    "Error! Please enter a valid price",
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }
        }
    }

    private fun deleteProduct(productId: Long, product: Products) {
        val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products/$productId"

        val request = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            { response ->
                // Handle response upon successful deletion
            },
            { error ->
                // Handle error in case deletion fails
            }
        )
        requestQueue?.add(request)
    }



    private fun updateProduct(productId: Long) {
        val productName = findViewById<EditText>(R.id.name).text.toString()
        val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
        val price = findViewById<EditText>(R.id.priceEditText).text.toString()
        val category = findViewById<Spinner>(R.id.categories).selectedItem.toString()

        //val imageData = getImageData()

        try {
            val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products/$productId"
            val request = object : VolleyFormData(
                Method.PUT, url,
                Response.Listener { response ->
                    // Handle successful update
                    val productListIntent = Intent(this, MainActivity::class.java)
                    startActivity(productListIntent)
                    overridePendingTransition(R.anim.in_left_anim, R.anim.out_right_anim)
                },
                Response.ErrorListener { error ->
                    val snackbar = Snackbar.make(
                        findViewById<View>(android.R.id.content),
                        "Error adding product: ${error.message}",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }) {

                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["name"] = productName
                    params["description"] = description
                    params["price"] = price
                    val categoryId = getCategoryIDByName(category)
                    params["categoryID"] = categoryId.toString()
                    return params
                }
                override fun getByteData(): Map<String, ByteArray>? {
                    val params = HashMap<String, ByteArray>()
                for ((index, imageData) in imageArray.withIndex())
                {
                    Log.d("image : ", "${imageData}")
                    try {
                        params["images[$index]"] = Base64.decode(imageData, Base64.DEFAULT)
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(this@addProduct, "${e.printStackTrace()}", Toast.LENGTH_LONG).show()
                       // e.printStackTrace()
                    }
                }

                return params
            }
        }

            requestQueue = Volley.newRequestQueue(this)
            requestQueue?.add(request)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } catch (e: NumberFormatException) {
            // Handle NumberFormatException
            // Show error message
            // ...
        }
    }


    private fun getCategoryIDByName(categoryName: String): Long? {
        val foundCategory = categories.find { it.categoryName == categoryName }
        return foundCategory?.id ?: null
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.in_left_anim, R.anim.out_right_anim)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position > 0) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
