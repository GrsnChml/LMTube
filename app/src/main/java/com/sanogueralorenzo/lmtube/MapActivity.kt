package com.sanogueralorenzo.lmtube

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.activity_map.*

object Constant {
    val MAP = "MAP"
    val TUBE = "tube.png"
    val NIGHT = "night.png"
    val RAIL = "rail.png"
}

class MapActivity : AppCompatActivity() {

    val sp: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (!item.isChecked) {
            when (item.itemId) {
                R.id.navigation_tube -> showMap(Constant.TUBE)
                R.id.navigation_night -> showMap(Constant.NIGHT)
                R.id.navigation_rail -> showMap(Constant.RAIL)
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        imageView.setMinimumScaleType(2)
        bottomNavigationView.setOnNavigationItemSelectedListener(listener)
        getInitialMap()
    }

    fun getInitialMap() {
        val lastMap = loadLastMap()
        setImage(lastMap)
        setChecked(lastMap)
    }

    fun setImage(asset: String) = imageView.setImage(ImageSource.asset(asset))

    fun setChecked(type: String) {
        var index = 0
        when (type) {
            Constant.TUBE -> index = 0
            Constant.NIGHT -> index = 1
            Constant.RAIL -> index = 2
        }
        bottomNavigationView.menu.getItem(index).isChecked = true
    }

    fun showMap(map: String) {
        saveLastMap(map)
        setImage(map)
    }

    fun saveLastMap(map: String) = sp.edit().putString(Constant.MAP, map).apply()

    fun loadLastMap(): String = sp.getString(Constant.MAP, Constant.TUBE)
}
