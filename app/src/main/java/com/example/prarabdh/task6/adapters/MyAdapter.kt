package com.example.prarabdh.task6.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.prarabdh.task6.GameDescData
import com.example.prarabdh.task6.ListenerObject
import com.example.prarabdh.task6.R


class MyAdapter(private val images: IntArray, private val names: Array<String>, private val pointsToUnlock: IntArray, private val playerPoints: Int, private val listener1: ListenerObject): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        internal var button1: Button = view.findViewById(R.id.button1)
        internal var button2: Button = view.findViewById(R.id.button2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.game_images, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = images.size / 2

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.button1.setBackgroundResource(images[(2 * position)])
        holder.button2.setBackgroundResource(images[((2 * position) + 1)])
        holder.button1.text = names[(2 * position)]
        holder.button2.text = names[((2 * position) + 1)]
        holder.button1.setOnClickListener {

            if (playerPoints >= pointsToUnlock[position]) {
                Log.i("Button", "This quiz is unlocked")
                //DataRetrieve().gameDescData(names[position])
                GameDescData.background = images[position]
                GameDescData.heading = names[position]
                listener1.listener!!.onDataRecieved()
            }
            else
            {
                Log.i("Button","This quiz is locked")
            }
        }

    }
}