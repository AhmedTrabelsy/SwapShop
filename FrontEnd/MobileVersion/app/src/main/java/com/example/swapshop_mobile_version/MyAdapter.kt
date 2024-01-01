package com.example.swapshop_mobile_version

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.swapshop_mobile_version.models.Products
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MyAdapter(private var myDataSet: ArrayList<Products>, private val context: Context): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var filteredDataSet: ArrayList<Products> = myDataSet
    private var itemClickListener: OnItemClickListener? = null
    private var requestQueue: RequestQueue? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.list_ligne,
            parent,false)
        return ViewHolder(vh)
    }

    private fun deleteProduct(productId: Long, product: Products) {
        val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products/$productId"

        val request = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            { response ->
                val position = myDataSet.indexOf(product)
                if (position != -1) {
                    myDataSet.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_LONG)
                        .show()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
           },
            { error ->
                //Toast.makeText(context, "Error deleting product: ${error.message}", Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        )
        requestQueue?.add(request)
    }

    private fun deleteProductForUpdate(productId: Long, product: Products) {
        val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products/$productId"

        val request = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            { response ->
                val position = myDataSet.indexOf(product)
                if (position != -1) {
                    myDataSet.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_LONG)
                        .show()
                }
            },
            { error ->
                //Toast.makeText(context, "Error deleting product: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        requestQueue?.add(request)
    }


    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
        val deleteButton = holder.itemView.findViewById<Button>(R.id.del)
        val editButton = holder.itemview.findViewById<Button>(R.id.edit)
        val current = filteredDataSet[position].productName
        val currentPrice = filteredDataSet[position].price
        val picture = holder.itemView.findViewById<ImageView>(R.id.productPic)
        holder.productName.text = current.toString()
        holder.price.text = currentPrice.toString() + " DT"
        val imageUrl = "http://34.199.239.78:8888/PRODUCT-SERVICE/${filteredDataSet[position].picturePath}"
        Log.d("MyAdapter", "Image URL: $imageUrl")
        Picasso.get().load(imageUrl).into(picture)

        deleteButton.setOnClickListener {
            val product = myDataSet[position]
            val builder = AlertDialog.Builder(holder.itemView.context)

            builder.setTitle("Confirm Deletion")
            builder.setMessage("Are you sure?")

            builder.setPositiveButton("Yes") { dialog, which ->
                requestQueue = Volley.newRequestQueue(context)
                product.id?.let { it1 -> deleteProduct(it1, product) }
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
                intent.putExtra("catId",product.category.id)
                intent.putExtra("picPath",product.picturePath)
                intent.putExtra("idProd",product.id)
                intent.putExtra("index", position.toString())
                //requestQueue = Volley.newRequestQueue(context)
                //deleteProductForUpdate(product.id,product)
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
        val productPic = itemview.findViewById(R.id.productPic) as ImageView
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}
