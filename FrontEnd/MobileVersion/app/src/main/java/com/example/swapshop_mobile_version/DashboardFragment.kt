package com.example.swapshop_mobile_version
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swapshop_mobile_version.MainActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

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
        val lineChart: LineChart = view.findViewById(R.id.ProductsAded)
        val values = ArrayList<String>()

        val description = Description()
        description.text = "Sold Products"
        description.setPosition(130f, 15f)
        lineChart.description = description

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.setDrawLabels(true)

        val minYValue = 0f
        val maxYValue = 100f
        val yAxisRange = maxYValue - minYValue

        yAxisLeft.axisMinimum = minYValue
        yAxisLeft.axisMaximum = maxYValue
        yAxisLeft.granularity = yAxisRange / 5

        val yAxisRight = lineChart.axisRight
        yAxisRight.setDrawLabels(false)

        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 40f))
        entries.add(Entry(1f, 80f))
        entries.add(Entry(2f, 60f))
        entries.add(Entry(3f, 20f))

        val dataSet = LineDataSet(entries, "Sales")
        dataSet.setCircleColor(android.R.color.black)
        dataSet.setDrawFilled(true)
        val lineData = LineData(dataSet)

        lineChart.data = lineData

        lineChart.invalidate()

        return view
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
