package com.example.prarabdh.task6

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.prarabdh.task6.adapters.ProfileAdapter
import com.example.prarabdh.task6.dataModels.PlayerData
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var btnSignOut: Button? = null
    private var txtUname: TextView? = null
    private var txtWin: TextView? = null
    private var txtLoose: TextView? = null
    private var txtEmail: TextView? = null
    private var imageView: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_profile

        auth = FirebaseAuth.getInstance()

        btnSignOut = view.findViewById(R.id.btnSignOut)
        btnSignOut!!.setOnClickListener {
            signOut()
        }

        txtUname = view.findViewById(R.id.txtuname)
        txtUname!!.text = PlayerData.udrUserName

        txtWin = view.findViewById(R.id.txtwin)
        txtWin!!.text= PlayerData.udrWin

        txtLoose = view.findViewById(R.id.txtloose)
        txtLoose!!.text= PlayerData.udrLoss

        txtEmail = view.findViewById(R.id.txtemail)
        txtEmail!!.text = PlayerData.udrEmail

        imageView = view.findViewById(R.id.imageViewAvatar)
        Glide.with(this@ProfileFragment).load( PlayerData.udrAvatar).into(imageView!!)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = ProfileAdapter()
        recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {

            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    private fun signOut() {

        auth.signOut()
        startActivity(Intent(activity, SignInActivity::class.java))

    }

}