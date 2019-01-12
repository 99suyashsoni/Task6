package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HomeFragment: Fragment(){

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var recyclerView: RecyclerView? = null
    private val images =  intArrayOf(R.drawable.blue1_wall,R.drawable.blue2_wall,R.drawable.blue_wall,R.drawable.green_wall,
                                    R.drawable.pink_wall,R.drawable.purple1_wall, R.drawable.purple2_wall,R.drawable.purple_wall,R.drawable.red_wall,
                                    R.drawable.yellow_wall)

    private val names= arrayOf("Game 1", "Game 2", "Game 3", "Game 4", "Game 5", "Game 6", "Game 7", "Game 8", "Game 9", "Game 10")

    private val pointsToUnlock = intArrayOf(0,0,0,100,200,500,1000,1500,2000,4000)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.home_fragment,container,false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_home

        val playerPoints = 1000//PlayerData.udrPoints.toInt()

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MyAdapter(images, names, pointsToUnlock, playerPoints, activity!!)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {

            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }
}