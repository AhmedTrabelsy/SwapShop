package com.example.swapshop_mobile_version

import android.content.Context
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
import com.example.swapshop_mobile_version.models.Products
import com.squareup.picasso.Picasso

class ProductsListCardsAdapter(private var myDataSet: ArrayList<Products>, private val context: Context) : RecyclerView.Adapter<ProductsListCardsAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = myDataSet[position].productName
        holder.price.text = myDataSet[position].price
        val imageUrl = "http://34.199.239.78:8888/PRODUCT-SERVICE/${myDataSet[position].picturePath}"
        Log.d("MyAdapter", "Image URL: $imageUrl")
        Picasso.get().load(imageUrl).into(holder.productPic)
        holder.productCard.setOnClickListener{
            Toast.makeText(context,"productsDetails",Toast.LENGTH_LONG).show()
        }
    }
}
