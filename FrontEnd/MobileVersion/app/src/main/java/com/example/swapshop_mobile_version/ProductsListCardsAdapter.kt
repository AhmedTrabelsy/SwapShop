package com.example.swapshop_mobile_version

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.example.swapshop_mobile_version.models.WishItems
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class ProductsListCardsAdapter(private var myDataSet: ArrayList<Products>, private val context: Context) : RecyclerView.Adapter<ProductsListCardsAdapter.ViewHolder>() {

    private var requestQueue: RequestQueue? = null
    private var whishItemsList = ArrayList<WishItems>()
    private val imagesUrls = ArrayList<String>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName = itemView.findViewById(R.id.productNameList) as TextView
        val price = itemView.findViewById(R.id.priceProductList) as TextView
        val productPic = itemView.findViewById(R.id.productPictureList) as ImageView
        val addToWishList = itemView.findViewById(R.id.AddToFavourite) as ImageButton
        val productCard = itemView.findViewById(R.id.listCard) as CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.products_cards,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }
    private fun jsonParseWishItems() {
        val url = "http://34.199.239.78:8888/WISHLIST-SERVICE/1"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val userProducts = response.getJSONArray("products")

                for (i in 0 until userProducts.length()) {
                    val product = userProducts.optJSONObject(i)

                    if (product != null) {
                        // Parsing for non-null product object
                        val userId = response.optLong("user_id")
                        val productId = product.optLong("id")
                        val productName = product.optString("name")
                        val price = product.optDouble("price")
                        val description = product.optString("description")

                        val category = product.optJSONObject("category")
                        val categoryId = category?.optLong("id") ?: 0
                        val categoryName = category?.optString("name") ?: "Unknown"

                        val imagesArray = product.optJSONArray("images")
                        val imagesUrls = ArrayList<String>()

                        imagesArray?.let {
                            for (j in 0 until it.length()) {
                                val imageObject = it.optJSONObject(j)
                                val imageName = imageObject?.optString("name")
                                imageName?.let {
                                    val fullImageUrl = "$it"
                                    imagesUrls.add(fullImageUrl)
                                }
                            }
                        }

                        val imageUrl = if (imagesUrls.isNotEmpty()) imagesUrls[0] else "@drawable/app_background.png"

                        val newUser = WishItems(
                            userId,
                            Products(
                                productId,
                                productName,
                                price.toString(),
                                description,
                                Categories(categoryId, categoryName),
                                imageUrl
                            )
                        )
                        whishItemsList.add(newUser)
                        Log.d("widhedItems", "${whishItemsList[0].userId}")
                    }
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })

        requestQueue?.add(request)
    }

    fun deleteFromWishlist(user_id: Long, product_id: Long) {
        val url = "http://34.199.239.78:8888/WISHLIST-SERVICE/$user_id/$product_id"

        val request = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            { response ->

            },
            { error ->

            }
        )
        requestQueue?.add(request)
        notifyDataSetChanged()
    }
    fun addToWishlist(user_id: Long, product_id: Long) {
        val url = "http://34.199.239.78:8888/WISHLIST-SERVICE"

        val jsonObject = JSONObject().apply {
            put("user_id", user_id)
            put("product_id", product_id)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonObject,
            { response ->
            },
            { error ->
            }
        )
        requestQueue?.add(request)
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = myDataSet[position].productName
        holder.price.text = myDataSet[position].price
        val imageUrl = "http://34.199.239.78:8888/PRODUCT-SERVICE/${myDataSet[position].picturePath}"
        Log.d("MyAdapter", "Image URL: $imageUrl")
        Picasso.get().load(imageUrl).into(holder.productPic)
        holder.productCard.setOnClickListener{
            val intent = Intent(context, ProductsDetails::class.java)

            intent.putExtra("categoryName", myDataSet[position].category.categoryName)
            intent.putExtra("productName", myDataSet[position].productName)
            intent.putExtra("priceProduct", myDataSet[position].price)
            intent.putExtra("description", myDataSet[position].description)
            intent.putExtra("imagePath",myDataSet[position].picturePath)
            intent.putStringArrayListExtra("imageList", imagesUrls)
            context.startActivity(intent)
        }
        var isWished = false
        for (item in whishItemsList){
            if (item.product?.id == myDataSet[position].id) {
                isWished = true
            }else {
                isWished = false
            }
        }
        holder.addToWishList.setOnClickListener {
            if (isWished) {
                requestQueue = Volley.newRequestQueue(context)
                deleteFromWishlist(1L,myDataSet[position].id)
                holder.addToWishList.setImageResource(R.drawable.ic_no_wished)
                notifyDataSetChanged()
            } else {
                requestQueue = Volley.newRequestQueue(context)
                addToWishlist(1L,myDataSet[position].id)
                holder.addToWishList.setImageResource(R.drawable.ic_wished)
                notifyDataSetChanged()
            }
            isWished = !isWished
        }


    }
}
