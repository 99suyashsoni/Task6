package com.example.prarabdh.task6

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.prarabdh.task6.R.id.navigation_profile
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment: Fragment(){
    private lateinit var auth: FirebaseAuth
    private var btnSignOut: Button? = null
    private var txtuname: TextView? = null
    private var imageView: ImageView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment,container,false)
//        val bottomNav: BottomNavigationView = HomeActivity().findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_profile

        auth = FirebaseAuth.getInstance()

        val btnSignOut: Button = view.findViewById(R.id.btnSignOut)

        val txtuname: TextView = view.findViewById(R.id.txtuname)
        val imageView: ImageView = view.findViewById(R.id.imageView2)
       // SignOut not implemented perfectly after pressing signout close the app and then open again u will be logged out

        btnSignOut.setOnClickListener(View.OnClickListener {
            signOut()

        })
        txtuname.text="UserName "+ UserDataRetrive.udrUserName
        Glide.with(this@ProfileFragment).load( UserDataRetrive.udrAvtar).into(imageView)

        return view
    }





    private fun signOut() {
        auth.signOut()


    }

}