package com.example.swapshop_mobile_version

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var email : EditText
    private lateinit var phone : EditText

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
        (activity as? MainActivity)?.setActionBarTitle("Settings")
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_settings, container, false)


        firstName = view.findViewById(R.id.SettingsfirstName)
        lastName = view.findViewById(R.id.SettingslastName)
        email = view.findViewById(R.id.Settingsemail)
        phone = view.findViewById(R.id.SettingsPhone)

        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


        val getUserData = RetrofitClient.getRetrofitInstance().create(GetUserData::class.java)
        val token = "Bearer " + SharedPreference(context).getValueString("accessToken")
        val call: Call<getUserResponse> = getUserData.getData(token)

        call.enqueue(object : Callback<getUserResponse> {
            override fun onResponse(call: Call<getUserResponse>, response: Response<getUserResponse>) {
                Log.d("userResponse","${response}")
                if (response.isSuccessful) {
                    firstName.setText(response.body()!!.firstName)
                    lastName.setText(response.body()!!.lastName)
                    email.setText(response.body()!!.email)
                    phone.setText(response.body()!!.phoneNumber)
                }
            }

            override fun onFailure(call: Call<getUserResponse>, t: Throwable) {
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}