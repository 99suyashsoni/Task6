package com.example.prarabdh.task6

import android.media.Image
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.prarabdh.task6.R.attr.data
import com.example.prarabdh.task6.R.id.navigation_leaderboard
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import android.R.attr.name
import android.support.v7.widget.RecyclerView.GONE
import android.view.WindowManager
import android.widget.ProgressBar
import com.example.prarabdh.task6.PlayerData.currentusermodel
import java.lang.System.console


class LeaderboardFragment : Fragment() {

    private var textViewUname: TextView? = null
    private var textViewLeaderboard: TextView? = null
    private var textViewTotalPoints: TextView? = null
    private var textViewPosition: TextView? = null
    private var imageViewAvatar: ImageView? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var mleaderboardAdapter: RecyclerView.Adapter<*>
    private lateinit var mlayoutManager: LayoutManager
    private var progressBar: ProgressBar? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.leaderboard_fragment, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar!!.visibility = View.VISIBLE
        progressBar!!.isIndeterminate = true
        textViewUname = view.findViewById(R.id.textViewUname)
        textViewLeaderboard = view.findViewById(R.id.textViewLeaderBoard)
        textViewTotalPoints = view.findViewById(R.id.textViewTotalPoints)
        textViewPosition = view.findViewById(R.id.textViewPosition)
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar)



        val listenerObject5 = ListenerObject()
        listenerObject5.setCustomObjectListener(object : ListenerObject.Listener{

            override fun onDataRecieved() {



                textViewUname!!.text = PlayerData.udrUserName
                textViewTotalPoints!!.text = PlayerData.udrPoints.toString()
                Glide.with(this@LeaderboardFragment).load(PlayerData.udrAvatar).into(imageViewAvatar!!)
                textViewLeaderboard!!.text = "Leadorboard"
                textViewPosition!!.text =(PlayerData.datasetLeaderboard.indexOf(PlayerData.currentusermodel!!) + 1).toString()
                mlayoutManager = LinearLayoutManager(activity)

                mleaderboardAdapter = LeaderboardAdapter(context, PlayerData.datasetLeaderboard)

                recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                    setHasFixedSize(true)
                    layoutManager = mlayoutManager
                    adapter = mleaderboardAdapter


                }
                mleaderboardAdapter.notifyDataSetChanged()
                progressBar!!.visibility = View.GONE

            }

        })


        val listenerObject6 = ListenerObject()
        listenerObject6.setCustomObjectListener(object : ListenerObject.Listener{

            override fun onDataRecieved() {
               textViewLeaderboard!!.text="try again"


            }

        })


        DataRetrieve().getLeadorboardData(listenerObject5,listenerObject6)
        return view
    }

}
