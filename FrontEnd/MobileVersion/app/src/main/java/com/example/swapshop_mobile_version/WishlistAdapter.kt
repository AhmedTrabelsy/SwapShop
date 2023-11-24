package com.example.swapshop_mobile_version

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.models.Products

class WishlistAdapter(
    private val wishlist: List<Products>,
    private val context: Context
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.priceProduct)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_wishlist_fragment, parent, false)

        return WishlistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val currentItem = wishlist[position]

        holder.productName.text = currentItem.productName
        holder.productPrice.text = currentItem.price

        // Load your image using a library like Picasso or Glide
        // Example with Glide:
        // Glide.with(context).load(currentItem.picturePath).into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return wishlist.size
    }

    companion object {
        fun notifyDataSetChanged() {
            TODO("Not yet implemented")
        }
    }
}
