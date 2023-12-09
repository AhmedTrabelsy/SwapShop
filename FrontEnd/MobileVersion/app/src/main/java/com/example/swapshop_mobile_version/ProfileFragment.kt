package com.example.swapshop_mobile_version

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.swapshop_mobile_version.RetrofitClient.Companion.getRetrofitInstance
import com.example.swapshop_mobile_version.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var textViewFullName : TextView
    private lateinit var textViewName : TextView
    private lateinit var textViewEmail : TextView
    private lateinit var textViewPhone : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity)?.setActionBarTitle("Profile")        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_profile, container, false)


        textViewFullName = view.findViewById(R.id.profileFullName)
        textViewName = view.findViewById(R.id.profileName)
        textViewEmail = view.findViewById(R.id.profileEmail)
        textViewPhone = view.findViewById(R.id.profilePhoneNumber)


        val sharedPreferenceInstance = SharedPreference(requireContext())
        val accessToken = sharedPreferenceInstance.getValueString("accessToken")
        val retrofit = getRetrofitInstance()
        val apiService = retrofit.create(GetUserData::class.java)
        val call = apiService.getData("Bearer $accessToken")


        call.enqueue(object : Callback<getUserResponse> {
            override fun onResponse(call: Call<getUserResponse>, response: Response<getUserResponse>) {
                if (response.isSuccessful) {
                    textViewFullName.setText(response.body()!!.firstName + " " + response.body()!!.lastName)
                    textViewName.setText(response.body()!!.firstName)
                    textViewEmail.setText(response.body()!!.email)
                    textViewPhone.setText(response.body()!!.phoneNumber)

                } else {
                    throw error("Request is not successful : $response")
                }
            }

            override fun onFailure(call: Call<getUserResponse>, t: Throwable) {
                // Handle the failure case
            }
        })



        return view;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}