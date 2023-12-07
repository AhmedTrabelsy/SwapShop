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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import org.json.JSONException
import java.time.format.DateTimeFormatter
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

        requestQueue = Volley.newRequestQueue(requireContext())
        jsonParse(view)

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

        // Inflate the layout for this fragment
        return view
    }

    private fun jsonParse(view: View) {
        val url = "http://34.199.239.78:8888/PRODUCT-SERVICE/products"
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                for (i in 0 until response.length()) {
                    val product = response.getJSONObject(i)
                    val id = product.getLong("id")
                    val productName = product.getString("name")
                    val price = product.getDouble("price")
                    val description = product.getString("description")
                    val category = product.getJSONObject("category")
                    val categoryName = category.getString("name")
                    val catId = category.getLong("id")
                    val imagesArray = product.getJSONArray("images")
                    for (i in 0 until imagesArray.length()) {
                        val imageUrl = imagesArray.getString(i)
                        imagesArrays.add(imageUrl)
                    }
                    var imageUrl = "@drawable/app_background.png"
                    if (imagesArray.length() > 0) {
                        val firstImage = imagesArray.getJSONObject(0)
                        val imageName = firstImage.getString("name")
                        imageUrl = imageName
                        Log.d("uri's", "$imageUrl")
                    } else {
                        Log.e("MainActivity", "JSONArray is empty")
                    }

                    val newUser = Products(
                        id,
                        productName,
                        price.toString(),
                        description,
                        Categories(catId, categoryName),
                        imageUrl,
                    )
                    filterList.add(newUser)
                    values.add(newUser)
                    recyclerView = view.findViewById(R.id.productList)
                    manager = LinearLayoutManager(requireContext())
                    myAdapter = MyAdapter(filterList, requireContext())

                    recyclerView.layoutManager = manager
                    recyclerView.adapter = myAdapter
                    // Set click listener for the adapter
                    myAdapter.setOnItemClickListener(this)

                   // Log.d("in productsFragments","$values")
                    myAdapter.notifyDataSetChanged()
                    /* values.add(newUser)
                     filterList.add(newUser)*/

                }
                // Log.d("from main","$newUser")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
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