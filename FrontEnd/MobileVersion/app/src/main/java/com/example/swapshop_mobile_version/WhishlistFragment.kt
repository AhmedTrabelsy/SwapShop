package com.example.swapshop_mobile_version

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapshop_mobile_version.models.Products
import com.example.swapshop_mobile_version.models.WishItems

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
        manager = GridLayoutManager(requireContext(),2)
        myAdapter = WhishListAdapter(values, requireContext())

        recyclerView.layoutManager = manager
        recyclerView.adapter = myAdapter
        val wishedNumber = view.findViewById<TextView>(R.id.titleItems)

        val productsList: ArrayList<WishItems>? = arguments?.getParcelableArrayList<Parcelable>("wishItems")?.mapNotNull { it as? WishItems }?.toCollection(ArrayList())
        productsList?.let {
            values.addAll(it)
            myAdapter.notifyDataSetChanged()
        }
        wishedNumber.text = "My Favourite ( ${values.size} )"

        return view
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