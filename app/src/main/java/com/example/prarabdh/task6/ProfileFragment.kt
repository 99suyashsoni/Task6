package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment: Fragment(){

    private lateinit var auth: FirebaseAuth
    private var btnSignOut: Button? = null
    private var txtUname: TextView? = null
    private var txtWin: TextView? = null
    private var txtLoose: TextView? = null
    private var txtTotal: TextView? = null
    private var txtEmail: TextView? = null
    private var imageView: ImageView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment,container,false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_profile

        auth = FirebaseAuth.getInstance()

        btnSignOut = view.findViewById(R.id.btnSignOut)
        txtUname = view.findViewById(R.id.txtuname)
        txtWin = view.findViewById(R.id.txtwin)
        txtLoose = view.findViewById(R.id.txtloose)
        txtTotal = view.findViewById(R.id.txttotal)

        txtEmail = view.findViewById(R.id.txtemail)
        imageView = view.findViewById(R.id.imageViewAvatar)
       // SignOut not implemented perfectly after pressing signout close the app and then open again u will be logged out

        btnSignOut!!.setOnClickListener {
            signOut()
        }

        txtUname!!.text= PlayerData.udrUserName
        txtEmail!!.text= PlayerData.udrEmail
        txtWin!!.text= PlayerData.udrWins
        txtLoose!!.text= PlayerData.udrLosses
        txtTotal!!.text= PlayerData.udrPoints
        Glide.with(this@ProfileFragment).load( PlayerData.udrAvtar).into(imageView!!)

        return view
    }


    private fun signOut() {
        auth.signOut()
       // startActivity(Intent(this@ProfileFragment, SignInActivity::class.java))
    }

}