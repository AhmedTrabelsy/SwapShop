package com.example.swapshop_mobile_version

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.models.Products

class MyAdapter(private var myDataSet: ArrayList<Products>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var filteredDataSet: ArrayList<Products> = myDataSet
    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.list_ligne,
            parent,false)
        return ViewHolder(vh)
    }


    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
        val deleteButton = holder.itemView.findViewById<Button>(R.id.del)
        val editButton = holder.itemview.findViewById<Button>(R.id.edit)
        val current = filteredDataSet[position].productName
        val currentPrice = filteredDataSet[position].price
        holder.productName.text = current.toString()
        holder.price.text = currentPrice.toString() + " DT"

        deleteButton.setOnClickListener {
            val product = myDataSet[position]
            val builder = AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Confirm Deletion")
            builder.setMessage("Are you sure ?")

            builder.setPositiveButton("Yes") { dialog, which ->
                myDataSet.remove(product)
                notifyDataSetChanged()
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }


        editButton.setOnClickListener{
            val product = myDataSet[position]
            val builder = AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Confirm edition Product")
            builder.setMessage("Are you sure ?")

            builder.setPositiveButton("Yes") { dialog, which ->
                val intent = Intent(holder.itemView.context, addProduct::class.java)
                intent.putExtra("productName", product.productName)
                intent.putExtra("price", product.price.toString())
                intent.putExtra("description",product.description.toString())
                intent.putExtra("category",product.category.categoryName)
                intent.putExtra("index", position.toString())
                holder.itemView.context.startActivity(intent)
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
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
