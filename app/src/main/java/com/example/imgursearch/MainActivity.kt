package com.example.imgursearch

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout ? = null
    var viewPager: ViewPager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mybutton = findViewById<Button>(R.id.button_link)
        mybutton.setOnClickListener {
            println("attemmp??")


           val intent = Intent(mybutton.context, webviewactivity::class.java)
            mybutton.context.startActivity(intent)
        }
    }
}
