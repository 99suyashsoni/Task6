package com.example.prarabdh.task6

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class MyAdapter(private val images: IntArray, private val names: Array<String>, private val pointsToUnlock: IntArray, private val playerPoints: Int): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val view:View) : RecyclerView.ViewHolder(view){

        internal var button: Button = view.findViewById(R.id.button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.game_images, parent,false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.button.setBackgroundResource(images[position])
        holder.button.text = names[position]
        holder.button.isClickable = (playerPoints >= pointsToUnlock[position])
        holder.button.setOnClickListener {

            Log.i("Button Check","Button Click Works")
            //HomeActivity().supportFragmentManager.beginTransaction().replace(R.id.homeFragment, GameDesc()).addToBackStack(null).commit()
        }
    }
}