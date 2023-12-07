package com.example.swapshop_mobile_version

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.example.swapshop_mobile_version.models.WishItems
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WhishlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WhishlistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: WhishListAdapter
    private var values = ArrayList<WishItems>()
    private var requestQueue: RequestQueue? = null

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
        val view = inflater.inflate(R.layout.fragment_whishlist, container, false)
        (activity as? MainActivity)?.setActionBarTitle("Wishlist")
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.items)
        requestQueue = Volley.newRequestQueue(requireContext())
        jsonParseWishItems(view)

        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.itemRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            values.clear()
            jsonParseWishItems(view)
            myAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

        val goingToMap = view.findViewById<FloatingActionButton>(R.id.floatingActionButtonForMap)
        goingToMap.setOnClickListener {
            /*val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, MapsFragment())
                .commit()*/
            val intent = Intent(requireContext(), OrdersActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun jsonParseWishItems(view: View) {
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
                        values.add(newUser)
                        manager = GridLayoutManager(requireContext(),2)
                        myAdapter = WhishListAdapter(values, requireContext())

                        recyclerView.layoutManager = manager
                        recyclerView.adapter = myAdapter
                        val wishedNumber = view?.findViewById<TextView>(R.id.titleItems)

                        /*val productsList: ArrayList<WishItems>? = arguments?.getParcelableArrayList<Parcelable>("wishItems")?.mapNotNull { it as? WishItems }?.toCollection(ArrayList())
                        productsList?.let {
                            values.addAll(it)
                            myAdapter.notifyDataSetChanged()
                        }*/
                        wishedNumber?.text = "My Favourite ( ${values.size} )"
                        myAdapter.notifyDataSetChanged()
                        Log.d("Values from WishlistFragment","${values}")
                        Log.d("widhedItems From Fragment", "${values[0].userId}")
                    }
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })

        requestQueue?.add(request)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WhishlistFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WhishlistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}