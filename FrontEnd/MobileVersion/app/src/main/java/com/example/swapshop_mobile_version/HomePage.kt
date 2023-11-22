package com.example.swapshop_mobile_version

import android.content.Intent
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView


class HomePage  {
    private var menuBtn: ImageView? = null
    private var cartBtn: ImageView?= null
    private var imageSlider: ImageSlider?= null
    private var drawerLayout: DrawerLayout?= null
    private var navView: NavigationView?= null
    private var shopNowBtn: Button?= null
    private var cartTotal: TextView?= null
    private var searchView: SearchView?= null
    private  fun setupImageSlider(){

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.ps52))
        imageList.add(SlideModel(R.drawable.xbox1))
        imageList.add(SlideModel(R.drawable.fifa))
        imageSlider?.setImageList(imageList,ScaleTypes.CENTER_CROP)


    }

}