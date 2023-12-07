package com.example.swapshop_mobile_version
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.swapshop_mobile_version.MainActivity
import com.example.swapshop_mobile_version.models.Categories
import com.example.swapshop_mobile_version.models.Products
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.json.JSONException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var ProductValues = ArrayList<Products>()
    private var requestQueue: RequestQueue? = null
    private lateinit var imageUrl: String
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
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        (activity as? MainActivity)?.setActionBarTitle("Dashboard")
        requestQueue = Volley.newRequestQueue(requireContext())
        jsonParse(view)

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
                    val createdDate = product.getString("created_at")
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
                    ProductValues.add(newUser)

                    val formatter = DateTimeFormatter.ISO_DATE_TIME
                    val dateTime = LocalDateTime.parse(createdDate, formatter)

                    /*val monthValue = dateTime.monthValue
                    Log.d("mounths","${monthValue}")*/

                    val productsByMonth = ProductValues.groupBy { product ->
                        // Extract month from the product's created_at date
                        val monthValue = dateTime.monthValue
                        monthValue // LocalDateTime should match the date format in the JSON
                    }
                    val productCountPerMonth = productsByMonth.mapValues { (_, productList) ->
                        productList.size
                    }
                    val lineChart: LineChart = view.findViewById(R.id.ProductsAded)
                    val values = ArrayList<String>()
                    val descriptionChart = Description()
                    descriptionChart.text = "Products Added In Months"
                    lineChart.description = descriptionChart

                    val xAxis = lineChart.xAxis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM // Set X-axis position to bottom
                    xAxis.granularity = 1f // Set the granularity to 1 month
                    xAxis.axisMinimum = 0f // Start at 0 for the first month
                    xAxis.axisMaximum = 11f // End at 11 for the twelfth month

                    val yAxisLeft = lineChart.axisLeft
                    yAxisLeft.setDrawLabels(true)

                    val minYValue = 0f
                    val maxYValue = 100f
                    val yAxisRange = maxYValue - minYValue

                    yAxisLeft.axisMinimum = minYValue
                    yAxisLeft.axisMaximum = maxYValue
                    yAxisLeft.granularity = yAxisRange / 5

                    val monthNames = arrayOf(
                        "January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"
                    )

                    val entries = ArrayList<Entry>()
                    for (i in 0 until monthNames.size) {
                        val count = productCountPerMonth[i + 1]?.toFloat() ?: 0f
                        entries.add(Entry(i.toFloat(), count))
                    }
                    val xAxisValueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return if (value >= 0 && value < monthNames.size) {
                                monthNames[value.toInt()]
                            } else {
                                ""
                            }
                        }
                    }

                    xAxis.valueFormatter = xAxisValueFormatter

                    val dataSet = LineDataSet(entries, "Products Added")
                    dataSet.setCircleColor(android.R.color.black)
                    dataSet.setDrawFilled(true)
                    val lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate()


                }
                // Log.d("from main","$newUser")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
