package com.example.swapshop_mobile_version

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.example.swapshop_mobile_version.models.Products

class WishlistFragment : Fragment(), WishlistAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var wishlistAdapter: WishlistAdapter
    private var requestQueue: RequestQueue? = null
    private var wishlistProducts = ArrayList<Products>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist_fragment, container, false)

        // Initialize RecyclerView and adapter
        recyclerView = view.findViewById(R.id.wishlist)
        manager = LinearLayoutManager(requireContext())
        wishlistAdapter = WishlistAdapter(wishlistProducts, requireContext())

        recyclerView.layoutManager = manager
        recyclerView.adapter = wishlistAdapter
        wishlistAdapter.setOnItemClickListener(this)

        // TODO: Fetch wishlist products and populate wishlistProducts list

        return view
    }

    // Implement any additional methods or functions as needed

    override fun onItemClick(position: Int) {
        // Handle item click in the wishlist
        // You can navigate to a details screen or perform any other action
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WishlistFragment().apply {
                arguments = Bundle().apply {
                    // You can pass any arguments if needed
                }
            }
    }
}
