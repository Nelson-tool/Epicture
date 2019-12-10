package com.example.imgursearch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.imgursearch.Utils.Globals
import com.example.imgursearch.Utils.Images
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import org.json.JSONArray
import org.json.JSONObject

@Suppress("DEPRECATION")
class Main3Activity : AppCompatActivity() {
        private val context : Context by lazy { this }
        private lateinit var adapter : imageAdapter
        private val navigation : BottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.navigation) }
        private val mOnNavigatonSelectedLister = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId) {
            R.id.navigation_home -> {
                startActivity(Intent(this, Main2Activity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                startActivity(Intent(this, Main3Activity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_upload -> {
                startActivity(Intent(this, Main4Activity::class.java))
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        this.navigation.setOnNavigationItemSelectedListener(this.mOnNavigatonSelectedLister)

        var nameEditText: EditText
        var searchBtn: RelativeLayout

        nameEditText = findViewById(R.id.nameEditText)
        searchBtn = findViewById(R.id.searchBtn)

        this.adapter = imageAdapter(Globals.arrayListforSearch)
        myRecyclerView.layoutManager = GridLayoutManager(this, 2)
        myRecyclerView.adapter = this.adapter

        searchBtn.setOnClickListener {
            var name = nameEditText.text.toString()
            val queue = Volley.newRequestQueue(this)
            val url = "https://api.imgur.com/3/gallery/search?q=${name}"
            val stringRequest = object : StringRequest(Method.GET, url,
                Response.Listener<String> { response ->
                    val imagesObject : Images = Gson().fromJson<Images>(response, Images::class.java)
                    Globals.arrayListforSearch.clear()
                    for (image in imagesObject.data) {
                        Globals.arrayListforSearch.add(image.link)
                    }
                    Log.d("ARRAY SIZE", Globals.arrayListforSearch.size.toString())
                    this.adapter.notifyDataSetChanged()


                },
                Response.ErrorListener { first_fragment.text = "That didn't work!" }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Client-ID 055738600fe96e9"
                    return headers
                }
            }
            queue.add(stringRequest)
            println("hello")
        }
    }

}


