package com.example.prarabdh.task6

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class MyAdapter(private val images: IntArray): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val view:View) : RecyclerView.ViewHolder(view){

        internal var imageButton: ImageButton = view.findViewById(R.id.imageButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.game_images, parent,false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.imageButton.setImageResource(images[position])

    }
}