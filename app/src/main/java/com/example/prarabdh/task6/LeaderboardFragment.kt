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


class LeaderboardFragment : Fragment() {

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
    private var currentuserposition = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.leaderboard_fragment, container, false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = R.id.navigation_leaderboard
        progressBar = view.findViewById(R.id.progressBar)
        progressBar!!.visibility = View.VISIBLE
        progressBar!!.isIndeterminate = true
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
        if (PlayerData.udrPoints.equals("")) {

        } else {
            currentusermodel = LeaderboardDataModel(PlayerData.udrPoints.toInt(), PlayerData.udrAvatar, PlayerData.udrUserName)
        }
        textViewUname!!.text = PlayerData.udrUserName
        textViewTotalPoints!!.text = PlayerData.udrPoints.toString()
        Glide.with(this@LeaderboardFragment).load(PlayerData.udrAvatar).into(imageViewAvatar!!)
        textViewLeaderboard!!.text = "Leadorboard"
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    var model: LeaderboardDataModel
                Log.d("LeaderboardFragment", "Size: ${dataSnapshot.children.count()}")

                for (dsp in dataSnapshot.children) {
                    var uname = dsp.child("Username").value!!.toString()
                    var points = dsp.child("Points").value!!.toString().toInt()
                    var avatar = dsp.child("Avatar").value!!.toString()

                    var model = LeaderboardDataModel(points, avatar, uname)

                    Log.d("LeaderboardFragment", "C2")

                    datasetLeaderboard.add(model)
                    datasetLeaderboard.sortByDescending { it.Points }
                }


                currentuserposition = datasetLeaderboard.indexOf(currentusermodel!!) + 1
                textViewPosition!!.text = currentuserposition.toString()
                mleaderboardAdapter.notifyDataSetChanged()


            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
                progressBar!!.visibility = View.GONE
            }
        })

        Log.d("LeaderboardFragment", "C1")
        progressBar!!.visibility = View.GONE
        return view
    }

}