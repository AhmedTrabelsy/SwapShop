package com.example.swapshop_mobile_version

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment(), MyAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: MyAdapter
    private var requestQueue: RequestQueue? = null
    private lateinit var imageUrl: String
    private var imagesArrays = ArrayList<String>()

    private var values = ArrayList<Products>()
    private var filterList = ArrayList<Products>()
    private var imageUrls :String = "@drawable/shopping_cart_833314.png"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)
        (activity as? MainActivity)?.setActionBarTitle("Products List")
        // Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.productList)
        manager = LinearLayoutManager(requireContext())
        myAdapter = MyAdapter(filterList, requireContext())

        recyclerView.layoutManager = manager
        recyclerView.adapter = myAdapter
        // Set click listener for the adapter
        myAdapter.setOnItemClickListener(this)

       /* val productIdExtra = arguments?.getLong("idProduct")
        val productNameExtra = arguments?.getString("productNameExtra")
        val priceProductExtra = arguments?.getString("priceProduct")
        val descriptionExtra = arguments?.getString("descriptionProduct")
        val catIdExtra = arguments?.getLong("catId")
        val catNameExtra = arguments?.getString("catName")
        val imagesArraysExtra = arguments?.getStringArrayList("imagesArray")*/
        Log.e("cv","helloWorld!")

        val productsList = arguments?.getParcelableArrayList<Products>("productsList")
        productsList?.let {
            values.addAll(it) // Add the received data to your values list
            myAdapter.notifyDataSetChanged() // Notify adapter after adding data
        }

        Log.d("in productsFragments","$values")

        val pc = Categories(60, "Pc's")
        val accessories = Categories(80, "Accessories")
        values.add(Products(5, "Pc Toshiba", "1500.0", "Pc cv", pc, imageUrls))
      //  requestQueue = Volley.newRequestQueue(requireContext())
        //jsonParse()
        filterList.addAll(values)
        myAdapter.notifyDataSetChanged()
        val searchItem = view.findViewById<SearchView>(R.id.searchBar)
        val editText = searchItem.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList.clear()
                editText.setTextColor(Color.BLACK);
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    values.forEach {
                        if (it.productName?.toLowerCase(Locale.getDefault())?.contains(searchText) == true) {
                            filterList.add(it)
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                } else {
                    filterList.addAll(values)
                }
                //myAdapter.submitList(filterList)
                return false
            }

        })

        val productName = arguments?.getString("productName")
        val productPrice =arguments?.getString("priceProduct")
        val productId = arguments?.getLong("id")
        val productDescription = arguments?.getString("description")
        val category = arguments?.getString("categoryName")
        val catId = arguments?.getLong("idCat")
        val image = arguments?.getString("imagePath")
        val indexString = arguments?.getString("index")
        val index = indexString?.toIntOrNull()

        if (index != null) {
            if (catId != null && productName != null && productPrice != null && productDescription != null && (index >= 0 && index < values.size)) {
                if (productId != null) {
                    values[index].id = productId
                }
                values[index].price = productPrice.toString()
                values[index].productName = productName
                values[index].description = productDescription
                values[index].category = Categories(catId, category)
                if (image != null) {
                    values[index].picturePath = image
                }
                //filterList.addAll(values)
            }
        } else {
            /* if (catId != null && productId != null && productName != null && productPrice != null && productDescription != null && index == null && image != null) {
                 values.add(
                     Products(
                         productId,
                         productName,
                         productPrice,
                         productDescription.toString(),
                         Categories(catId, category),
                         image
                     )
                 )
                 filterList.addAll(values)
             }*/
        }
        // Inflate the layout for this fragment
        return view
    }

    override fun onItemClick(position: Int) {
        val selectedProduct = values[position]

        val intent = Intent(requireActivity(), ProductsDetails::class.java)

        intent.putExtra("categoryName", selectedProduct.category.categoryName)
        intent.putExtra("productName", selectedProduct.productName)
        intent.putExtra("priceProduct", selectedProduct.price)
        intent.putExtra("description", selectedProduct.description)
        intent.putExtra("imagePath",selectedProduct.picturePath)
        intent.putStringArrayListExtra("imageList", imagesArrays)

        startActivity(intent)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}