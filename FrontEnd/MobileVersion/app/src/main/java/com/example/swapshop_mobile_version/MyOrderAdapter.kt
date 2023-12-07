package com.example.swapshop_mobile_version

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.models.Order
import com.example.swapshop_mobile_version.models.Products

class MyOrderAdapter(private var myDataSet: ArrayList<Order>, private val context: Context) :
    RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val orderRef = itemView.findViewById(R.id.orderRef) as TextView
        val productName = itemView.findViewById(R.id.productName) as TextView
        val orderDate = itemView.findViewById(R.id.date) as TextView
        val billingAdress = itemView.findViewById(R.id.billingAdress) as TextView
        val goToMap = itemView.findViewById(R.id.shipping) as ImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.orderRef.text = "Order Ref : ${myDataSet[position].order_id.toString()}"
        holder.productName.text = "Shipping name : ${myDataSet[position].productName.toString()}"
        holder.orderDate.text = "Order Date: ${myDataSet[position].checkedDate.toString()}"
        holder.billingAdress.text = "Billing adress: ${myDataSet[position].billingAdress.toString()}"
        holder.goToMap.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            val bundle = Bundle()
            val yea = "yessForMap"
            bundle.putString("isCallingToMap",yea)
            context.startActivity(intent)
        }
    }
}
