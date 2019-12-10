package com.example.imgursearch
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.imgursearch.Utils.Globals
import com.example.imgursearch.Utils.Images
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class Fragment_one : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.imgur.com/3/account/me/images/"
        val recycler = view.findViewById<RecyclerView>(R.id.recycleview)


        val stringRequest = object : StringRequest(Method.GET, url,
            Response.Listener<String> { response ->
                val imagesObject : Images = Gson().fromJson<Images>(response, Images::class.java)
                Globals.arrayList.clear()
                for (image in imagesObject.data) {
                    Globals.arrayList.add(image.link)
                }
                Log.d("ARRAY SIZE", Globals.arrayList.size.toString())
                val adapter = imageAdapter(Globals.arrayList)
                recycler.adapter = adapter
                recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            },
            Response.ErrorListener { first_fragment.text = "That didn't work!" }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${Globals.accessToken}"
                return headers
            }
        }
        queue.add(stringRequest)
    }

}
