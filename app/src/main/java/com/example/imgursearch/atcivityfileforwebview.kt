package com.example.imgursearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.imgursearch.Utils.Globals
import kotlinx.android.synthetic.main.activity_webview.*

class webviewactivity : AppCompatActivity() {
    private val context : Context by lazy { this }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_webview)
        //webview_activity.setBackgroundColor(Color.BLACK)
        webview_activity.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=055738600fe96e9&response_type=token&state=APPLICATION_STATE")

        fun split_token(url: String): String {
            val str = url
            val separet = str.split("=|-|&".toRegex())
            var token = separet[2]
            var refres_token = separet[8]
            return (token)
        }


        fun split_username(url: String): String {
            val str = url
            val separet = str.split("=|-|&".toRegex())
            var username = separet[10]
            return (username)
        }

        webview_activity.webViewClient = object : WebViewClient()
        {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String
            ): Boolean {
                Log.d("URL", url)
                val accessToken = (split_token(url))
                val accessUsername = (split_username(url))
                val intent_two = Intent(context, Main2Activity::class.java)
                Globals.accessToken = accessToken
                Globals.accessUsername = accessUsername
                startActivity(intent_two)
                return true
            }
        }
    }
}