package com.example.swapshop_mobile_version

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.swapshop_mobile_version.models.WishItems
import com.squareup.picasso.Picasso

class WhishListAdapter(private var myDataSet: ArrayList<WishItems>, private val context: Context) :
    RecyclerView.Adapter<WhishListAdapter.ViewHolder>() {
    private var requestQueue: RequestQueue? = null
    var size = myDataSet.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName = itemView.findViewById(R.id.productNameList) as TextView
        val price = itemView.findViewById(R.id.priceProductList) as TextView
        val productPic = itemView.findViewById(R.id.productPictureList) as ImageView
        val addToWishList = itemView.findViewById(R.id.AddToFavourite) as ImageButton
        val productCard = itemView.findViewById(R.id.listCard) as CardView
        val cancelCheckout = itemView.findViewById(R.id.cancel) as ImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.products_cards, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
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
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = myDataSet[position].product?.productName
        holder.price.text = myDataSet[position].product?.price
        val imageUrl = "http://34.199.239.78:8888/PRODUCT-SERVICE/${myDataSet[position].product?.picturePath}"
        Log.d("MyAdapter WishList", "Image URL: $imageUrl")
        Picasso.get().load(imageUrl).into(holder.productPic)
        holder.productCard.setOnClickListener{
            Toast.makeText(context,"productsDetails", Toast.LENGTH_LONG).show()
        }
        holder.addToWishList.setImageResource(R.drawable.ic_checkout)
        holder.addToWishList.setOnClickListener {
            val sharePage = Intent(context, PaimantPage::class.java)
            val bundle = Bundle()
            bundle.putString("price",myDataSet[position].product?.price)
            bundle.putString("productName",myDataSet[position].product?.productName)
            sharePage.putExtras(bundle)
            context.startActivity(sharePage)
        }
        holder.cancelCheckout.setImageResource(R.drawable.ic_cancel)
        holder.cancelCheckout.setOnClickListener {
            requestQueue = Volley.newRequestQueue(context)
            val productToRemove = myDataSet[position].product?.id
            productToRemove?.let { productIdToRemove ->
                deleteFromWishlist(1L, productIdToRemove)
                val positionToDelete = myDataSet.indexOfFirst { product ->
                    product.product?.id == productIdToRemove
                }
                if (positionToDelete != -1) {
                    myDataSet.removeAt(positionToDelete)
                    notifyItemRemoved(positionToDelete)
                    Toast.makeText(context, "Wish Item deleted successfully", Toast.LENGTH_LONG).show()
                    val newSize = myDataSet.size
                    notifyItemRangeChanged(positionToDelete, newSize)
                    size = myDataSet.size -1
                }
            }
        }
    }
}
