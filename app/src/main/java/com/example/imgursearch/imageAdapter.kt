package com.example.imgursearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imgursearch.Utils.Globals
import android.widget.ImageView as ImageView1

class imageAdapter(val image: ArrayList<String>) : RecyclerView.Adapter<imageAdapter.ViewHolder>() {
     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         Glide.with(holder.itemView.context)
             .load(image[position])
             .placeholder(R.drawable.login_background)
             .into(holder.getImage())
     }

     override fun getItemCount(): Int {
         return image.size
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view: View = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)

         //view.layoutParams = AbsListView.LayoutParams(500, 500)
         return ViewHolder(view)
     }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         private val imageload: android.widget.ImageView = itemView.findViewById(R.id.imageView)
         fun getImage(): android.widget.ImageView {
             return this.imageload
         }


     }



 }