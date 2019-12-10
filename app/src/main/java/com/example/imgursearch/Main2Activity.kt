package com.example.imgursearch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imgursearch.Utils.Globals
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import androidx.recyclerview.widget.RecyclerView.Adapter as RecyclerViewAdapter

class Main2Activity : AppCompatActivity() {
    private val context : Context by lazy { this }
    private lateinit var adapter : imageAdapter
    private val navigation : BottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.navigation) }
    private val mOnNavigatonSelectedLister = BottomNavigationView.OnNavigationItemSelectedListener {item->
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
        setContentView(R.layout.activity_main2)

        val adapter = MyViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(Fragment_one(), "Online Post")
        adapter.addFragment(Fragment_two(), "Most Viral")
        adapter.addFragment(Fragment_third(), "PROFIL")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        this.navigation.setOnNavigationItemSelectedListener(this.mOnNavigatonSelectedLister)
    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()



        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size

        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }

}
