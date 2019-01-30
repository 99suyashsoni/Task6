package com.example.prarabdh.task6.fragmentClasses

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prarabdh.task6.DataRetrieve
import com.example.prarabdh.task6.ListenerObject
import com.example.prarabdh.task6.R
import com.example.prarabdh.task6.adapters.MyAdapter
import com.example.prarabdh.task6.dataModels.GameDescData
import com.example.prarabdh.task6.dataModels.PlayerData
import com.example.prarabdh.task6.fragmentClasses.GameDesc
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private var check1: Boolean = false
    private var check2: Boolean = false
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var recyclerView: RecyclerView? = null
    private val images = intArrayOf(R.drawable.blue1_wall, R.drawable.blue2_wall, R.drawable.blue_wall, R.drawable.green_wall,
            R.drawable.pink_wall, R.drawable.purple1_wall, R.drawable.purple2_wall, R.drawable.purple_wall, R.drawable.red_wall,
            R.drawable.yellow_wall)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_home

        val listenerObject3 = ListenerObject()

        listenerObject3.setCustomObjectListener(object : ListenerObject.Listener{

            override fun onDataRecieved() {
                Log.d("Activity", "Activity2 $activity!!")
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.homeFragment, GameDesc()).addToBackStack(null).commit()
            }

        })


        val listenerObject1 = ListenerObject()

        listenerObject1.setCustomObjectListener(object : ListenerObject.Listener {

            override fun onDataRecieved() {

                check1 = true
                if(check1 && check2)
                {
                    viewManager = LinearLayoutManager(activity)
                    viewAdapter = MyAdapter(images, GameDescData.names, PlayerData.pointsToUnlock, PlayerData.udrPoints, listenerObject3)
                    recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {

                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                    check1 = false
                }

            }
        })

        val listenerObject2 = ListenerObject()

        listenerObject2.setCustomObjectListener(object : ListenerObject.Listener {

            override fun onDataRecieved() {

                check2 = true
                if(check1 && check2)
                {
                    Log.d("Activity Check","Activity $activity")
                    viewManager = LinearLayoutManager(activity)
                    Log.d("Data Check","${GameDescData.names[0]} , ${PlayerData.udrPoints} , ${PlayerData.pointsToUnlock[6]} , $context ")
                    viewAdapter = MyAdapter(images, GameDescData.names, PlayerData.pointsToUnlock, PlayerData.udrPoints, listenerObject3)
                    recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {

                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                    check1 = false
                }

            }
        })

        DataRetrieve().getCategoryNames(listenerObject1)
        DataRetrieve().pointsUnlockData(listenerObject2)

        return view
    }


//    fun adapter(){
//
//        if(check1 && check2){
//
//            viewManager = LinearLayoutManager(activity)
//            viewAdapter = MyAdapter(images, names, PlayerData.pointsToUnlock, PlayerData.udrPoints, activity!!)
//            recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {
//
//                layoutManager = viewManager
//                adapter = viewAdapter
//            }
//        }
//    }
}
