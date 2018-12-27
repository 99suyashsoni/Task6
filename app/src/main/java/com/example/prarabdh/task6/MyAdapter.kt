package com.example.prarabdh.task6

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class MyAdapter(private val images: IntArray, private val names: Array<String>, private val pointsToUnlock: IntArray, private val playerPoints: Int, private val activity: FragmentActivity): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //private val gameDesc = GameDesc()

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
        holder.button.setOnClickListener {

            if (playerPoints >= pointsToUnlock[position])
            {
                Log.i("Button", "This quiz is unlocked")
//                DataRetrieve().gameDescData(names[position])
//                GameDescData.background = images[position]
//                GameDescData.heading = names[position]
                activity.supportFragmentManager.beginTransaction().replace(R.id.homeFragment, GameDesc()).addToBackStack(null).commit()
            }
            else
            {
                Log.i("Button","This quiz is locked")
            }
        }
    }
}