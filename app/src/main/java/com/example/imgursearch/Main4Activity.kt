package com.example.imgursearch

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.imgursearch.Utils.Globals
import com.example.imgursearch.Utils.Globals.Companion.input
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.image_item.*
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import android.os.AsyncTask.execute

import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class Main4Activity : AppCompatActivity() {


    private val navigation: BottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.navigation) }
    private val mOnNavigatonSelectedLister =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
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

    private val TAG = "Permission"
    private val READ_EXTERNAL = 101
    var currentPAth:String?= null
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        this.navigation.setOnNavigationItemSelectedListener(this.mOnNavigatonSelectedLister)

        setupPermissions()
        uploads()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val uri = data?.data
                if (uri != null) {
                    Globals.input = this.contentResolver.openInputStream(uri)
                    val outputStream = ByteArrayOutputStream()
                    input.use { input ->
                        outputStream.use { output ->
                            Globals.input?.copyTo(output)
                        }
                    }
                    val byteArray = outputStream.toByteArray()
                   val base64tostring =  byteArray.encodeBase64()
                    val outputString = String(base64tostring, Charsets.UTF_8)
                    Globals.string_tobody = outputString
                    Log.d("helllo", outputString)
                    sendcall()

                }
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }


    fun uploads() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_PICTURE)
        }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL)
    }


    fun ByteArray.encodeBase64(): ByteArray {
        val table = (CharRange('A', 'Z') + CharRange('a', 'z') + CharRange('0', '9') + '+' + '/').toCharArray()
        val output = ByteArrayOutputStream()
        var padding = 0
        var position = 0
        while (position < this.size) {
            var b = this[position].toInt() and 0xFF shl 16 and 0xFFFFFF
            if (position + 1 < this.size) b = b or (this[position + 1].toInt() and 0xFF shl 8) else padding++
            if (position + 2 < this.size) b = b or (this[position + 2].toInt() and 0xFF) else padding++
            for (i in 0 until 4 - padding) {
                val c = b and 0xFC0000 shr 18
                output.write(table[c].toInt())
                b = b shl 6
            }
            position += 3
        }
        for (i in 0 until padding) {
            output.write('='.toInt())
        }
        return output.toByteArray()
    }

    fun sendcall() {
        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)
        val url = "https://api.imgur.com/3/image"

        //String Request initialized
       val mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Toast.makeText(applicationContext, "Logged In Successfully", Toast.LENGTH_SHORT).show()


        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Please make sure you enter correct password and username", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2.put("image", Globals.string_tobody)
                Log.d("helllo", Globals.string_tobody)
                Log.d("helllomopoo", Globals.accessToken)


                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }
           override fun getHeaders(): MutableMap<String, String> {
               val headers = HashMap<String, String>()
               headers["Authorization"] = "Bearer ${Globals.accessToken}"
               return headers
           }
        }
        mRequestQueue!!.add(mStringRequest!!)
    }

}
