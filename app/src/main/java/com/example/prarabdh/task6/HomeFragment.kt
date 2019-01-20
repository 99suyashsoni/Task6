package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var recyclerView: RecyclerView? = null
    private val images = intArrayOf(R.drawable.blue1_wall, R.drawable.blue2_wall, R.drawable.blue_wall, R.drawable.green_wall,
            R.drawable.pink_wall, R.drawable.purple1_wall, R.drawable.purple2_wall, R.drawable.purple_wall, R.drawable.red_wall,
            R.drawable.yellow_wall)
  // private var playerPoints: Int? = null
    private val database = FirebaseDatabase.getInstance()
    private var tmp: String = ""
    private val ref1 = database.getReference("Categories")

   private val names = arrayOf("")




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_home

        database.getReference("UnlockPoints").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dsp: DatabaseError) {

            }

            override fun onDataChange(dsp: DataSnapshot) {
                var x = dsp.value.toString()
                var j=0
                for (i in 0..x.length-1) {

                    if (x[i].toChar()==32.toChar()){

                        PlayerData.pointstoUnlock[j]=tmp.toInt()
                        j++
                        tmp=""
                    }else{

                        tmp=tmp+x[i]

                    }
                }
                ref1.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(dsp: DataSnapshot) {
                        var j=0
                        for(d in dsp.children){

                            names[j]=d.key.toString()
                            var t = names[j]
                            j++
                        }
                        /*viewManager = LinearLayoutManager(activity)
                        Log.d("TEST FINAL",PlayerData.pointstoUnlock.size.toString()+" "+PlayerData.udrPoints+" "+images.size+" "+names.size)
                        viewAdapter = MyAdapter(images, names, PlayerData.pointstoUnlock, 100, activity!!)
                        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {

                            layoutManager = viewManager
                            adapter = viewAdapter
                        }*/
                    }
                    override fun onCancelled(dsp: DatabaseError) {
                    }

                })
            }
        })


        //if(PlayerData.udrPoints!=""){ playerPoints = PlayerData.udrPoints.toInt()}
       //var  playerPoints = PlayerData.udrPoints.toInt()

        /*viewManager = LinearLayoutManager(activity)
        viewAdapter = MyAdapter(images, names, PlayerData.pointstoUnlock, PlayerData.udrPoints, activity!!)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {

            layoutManager = viewManager
            adapter = viewAdapter
        }*/
        return view
    }
}