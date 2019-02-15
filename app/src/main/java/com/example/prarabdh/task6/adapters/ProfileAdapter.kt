package com.example.prarabdh.task6.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.prarabdh.task6.R
import com.example.prarabdh.task6.dataModels.PlayerData
import com.example.prarabdh.task6.dataModels.QuizData

class ProfileAdapter: RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        internal var heading1: TextView = view.findViewById(R.id.textView4)
        internal var heading2: TextView = view.findViewById(R.id.textView12)
        internal var decs1: TextView = view.findViewById(R.id.textView16)
        internal var desc2: TextView = view.findViewById(R.id.textView17)
        internal var lock1: ImageView = view.findViewById(R.id.imageView5)
        internal var lock2: ImageView = view.findViewById(R.id.imageView2)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.achievements_list, parent, false)

        return ProfileViewHolder(view)
    }

    override fun getItemCount() = QuizData.udrAchievements.size / 2

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        holder.heading1.text = QuizData.udrAchievements[2*position].Name
        holder.heading2.text = QuizData.udrAchievements[2*position+1].Name
        holder.decs1.text = QuizData.udrAchievements[2*position].Value
        holder.desc2.text = QuizData.udrAchievements[2*position+1].Value

        if (PlayerData.udrAchievementsUnlocked[2*position] == '1'){
            holder.lock1.visibility = View.INVISIBLE
        }

        if (PlayerData.udrAchievementsUnlocked[2*position+1] == '1'){
            holder.lock2.visibility = View.INVISIBLE
        }
    }
}