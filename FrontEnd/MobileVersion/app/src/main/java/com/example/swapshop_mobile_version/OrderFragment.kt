package com.example.swapshop_mobile_version

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.models.Order

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment(), MyOrderAdapter.OnMapButtonClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: MyOrderAdapter
    private var values = ArrayList<Order>()
    private var requestQueue: RequestQueue? = null
    private var imagesArrays = ArrayList<String>()
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
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        (activity as? MainActivity)?.setActionBarTitle("Order Page")
        /*requestQueue = Volley.newRequestQueue(requireContext())
        jsonParse(view)*/
        val extras = arguments
        val id = extras?.getString("id")
        val productName = extras?.getString("productName")
        val orderDate = extras?.getString("orderDate")
        val billingAdress = extras?.getString("billingAdress")
       /* val goToMap = view.findViewById<ImageButton>(R.id.shipping)*/
        if (id != null && billingAdress!= null && orderDate != null && productName != null) {
            values.add(Order(id, billingAdress, orderDate, productName))
        }

        recyclerView = view.findViewById(R.id.orders)
        manager = LinearLayoutManager(requireContext())
        myAdapter = MyOrderAdapter(this,values, requireContext())

        recyclerView.layoutManager = manager
        recyclerView.adapter = myAdapter

        return view
    }
    override fun onMapButtonClicked() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_fragment, MapsFragment())
            .commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}