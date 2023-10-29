package com.example.swapshop_mobile_version

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.models.Products

class MyAdapter(private val myDataSet: ArrayList<Products>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.list_ligne,
            parent,false)
        return ViewHolder(vh)
    }


    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val deleteButton = holder.itemView.findViewById<Button>(R.id.del)
        val editButton = holder.itemview.findViewById<Button>(R.id.edit)
        var current = myDataSet[position].productName
        var currentPrice  = myDataSet[position].price
        holder.productName.text = current.toString()
        holder.price.text = currentPrice.toString() + " DT"

        deleteButton.setOnClickListener {
            val product = myDataSet[position]
            myDataSet.remove(product)
            notifyDataSetChanged()
        }
        editButton.setOnClickListener{
            val product = myDataSet[position]
            val intent = Intent(holder.itemView.context, addProduct::class.java)
            intent.putExtra("productName", product.productName)
            intent.putExtra("price", product.price.toString())
            intent.putExtra("description",product.description.toString())
            intent.putExtra("index", position.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }
    class ViewHolder(val itemview: View): RecyclerView.ViewHolder(itemview){
        val productName = itemView.findViewById(R.id.productName) as TextView
        val price = itemView.findViewById(R.id.priceProduct) as TextView
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}
