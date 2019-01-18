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
import java.lang.System.console



class LeaderboardFragment: Fragment() {
    private var textViewUname: TextView? = null
    private var textViewLeaderboard: TextView? = null
    private var textViewTotalPoints: TextView? = null
    private var textViewPosition: TextView? = null
    private var imageViewAvatar: ImageView? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var mleaderboardAdapter: RecyclerView.Adapter<*>
    private lateinit var mlayoutManager: LayoutManager
    private var datasetLeaderboard = ArrayList<LeaderboardDataModel>()
    private var currentusermodel: LeaderboardDataModel? = null
    private var progressBar: ProgressBar? = null
    private var progressBarPresent = false

//    private var datasetPoints = ArrayList<String>()
//    private var datasetAvatar = ArrayList<String>()
    private var mDatabase: DatabaseReference? = null
    private var currentuserposition=0;



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.leaderboard_fragment, container, false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = R.id.navigation_leaderboard
        progressBar = view.findViewById(R.id.progressBar)
        progressBar!!.setVisibility(View.VISIBLE)
        progressBar!!.setIndeterminate(true)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        textViewUname = view.findViewById(R.id.textViewUname)
        textViewLeaderboard = view.findViewById(R.id.textViewLeaderBoard)
        textViewTotalPoints = view.findViewById(R.id.textViewTotalPoints)
        textViewPosition = view.findViewById(R.id.textViewPosition)
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar)

        mlayoutManager = LinearLayoutManager(activity)
        mleaderboardAdapter = LeaderboardAdapter(context, datasetLeaderboard)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = mlayoutManager

            // specify an viewAdapter (see also next example)
            adapter = mleaderboardAdapter

        }

        currentusermodel= LeaderboardDataModel(PlayerData.udrPoints.toInt(),PlayerData.udrAvtar,PlayerData.udrUserName)
       // val `sort` = SortPoints()
//
//        `sort`.setSortPointsListener(object : SortPoints.SortPointsListener {
//           override fun onObjectReady(title: String) {
//                // Code to handle object ready
//            }
//
//          override  fun onDataLoaded(data: "ihu") {
//                // Code to handle data loaded from network
//                // Use the data here!
//            }
//        })


        textViewUname!!.text = PlayerData.udrUserName
        textViewTotalPoints!!.text = PlayerData.udrPoints
        Glide.with(this@LeaderboardFragment).load(PlayerData.udrAvtar).into(imageViewAvatar!!)
        textViewLeaderboard!!.text="Leaderboard"




        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

       // val sortRef = mDatabase!!.child(PlayerData.udrUserId)
              //  .orderByChild("Total Points")
     //   mDatabase!!.orderByChild("Total Points");
        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    var model: LeaderboardDataModel

                for (dsp in dataSnapshot.children) {
                    var uname = dsp.child("Username").value!!.toString()
                    var points = dsp.child("Total Points").value!!.toString().toInt()
                    var avatar = dsp.child("Avtar Img").value!!.toString()

                  var  model =  LeaderboardDataModel(points,avatar,uname)

                    datasetLeaderboard.add(model)
//                    datasetAvatar.add(avatar)
//                    datasetPoints.add(points)
//                    datasetUname.add(uname)
//
//                    for (i in 0 until datasetPoints.size - 1) {
//                        for (j in 0 until datasetPoints.size - i - 1) {
//                            if (Integer.parseInt(datasetPoints[j]) < Integer.parseInt(datasetPoints[j + 1])) {
//                                var temp = datasetPoints[j]
//                                datasetPoints[j] = datasetPoints[j + 1]
//                                datasetPoints[j + 1] = temp
//
//                                temp = datasetAvatar[j]
//                                datasetAvatar[j] = datasetAvatar[j + 1]
//                                datasetAvatar[j + 1] = temp
//
//                                temp = datasetUname[j]
//                                datasetUname[j] = datasetUname[j + 1]
//                                datasetUname[j + 1] = temp
//                            }
//                        }
//                    }
//                  datasetLeaderboard.sortWith(compareByDescending { it.Points })
                    datasetLeaderboard.sortByDescending { it.Points }
                }


                currentuserposition=  datasetLeaderboard.indexOf(currentusermodel!!)+1
                textViewPosition!!.text= currentuserposition.toString()
               mleaderboardAdapter.notifyDataSetChanged()
//                for (pass in 0 until (datasetPoints.size - 1)) {
//                    // A single pass of bubble sort
//                    for (currentPosition in 0 until (datasetPoints.size - pass - 1)) {
//                        // This is a sing


            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })







       // mleaderboardAdapter.notifyDataSetChanged()
//
//            val  n = datasetPoints.size
//
//        var j=0
//            for ( i = 0; i < n-1; i++)
//            for ( j = 0; j < n-i-1; j++)
//            if (arr[j] > arr[j+1])
//            {
//                // swap arr[j+1] and arr[i]
//                int temp = arr[j];
//                arr[j] = arr[j+1];
//                arr[j+1] = temp;
//            }

 //       bubbleSort()

   //     mleaderboardAdapter.notifyDataSetChanged()


        mleaderboardAdapter.notifyDataSetChanged()


progressBar!!.setVisibility(View.GONE)
        return view
    }



}

//
//    fun bubbleSort() {
//        for (pass in 0 until (datasetPoints.size - 1)) {
//            // A single pass of bubble sort
//            for (currentPosition in 0 until (datasetPoints.size - pass - 1)) {
//                // This is a single step
//                if (datasetPoints[currentPosition] > datasetPoints[currentPosition + 1]) {
//                    val tmp = datasetPoints[currentPosition]
//                    datasetPoints[currentPosition] = datasetPoints[currentPosition + 1]
//                    datasetPoints[currentPosition + 1] = tmp
//
//                    val tmp1 = datasetUname[currentPosition]
//                    datasetUname[currentPosition] = datasetUname[currentPosition + 1]
//                    datasetUname[currentPosition + 1] = tmp1
//
//                    val tmp2 = datasetPoints[currentPosition]
//                    datasetPoints[currentPosition] = datasetPoints[currentPosition + 1]
//                    datasetPoints[currentPosition + 1] = tmp2
//                }
//            }
//        }
//
//        mleaderboardAdapter.notifyDataSetChanged()
//
//    }
//}