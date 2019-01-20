package com.example.prarabdh.task6

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.prarabdh.task6.R.id.navigation_home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeActivity : AppCompatActivity()
{
     // Initialised database and authentication variables and moved all UI items to updateUI method to Successfully
//    fill them with logged in users data
    // Removed all uses of PlayerDataRetrieveDataRetrieve.kt class with static variables of PlayerData Class starting with usr**
    // anytime u need usrers data access UserDataRetrieve class static variables
    //IMP note*****
    // Loops for retreiving Achivements were causing OutOfMemory error and thus was unable to retrieve Achievements
    private lateinit var auth: FirebaseAuth
    private lateinit var  bottomNav: BottomNavigationView
    private var imageView: ImageView? = null
    private var userName: TextView? = null
    private var points: TextView? = null


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var selectedFragment: Fragment
        when (item.itemId) {
            navigation_home -> {
                selectedFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                selectedFragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).addToBackStack(null).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
                selectedFragment = LeaderboardFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).addToBackStack(null).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_random -> {
                selectedFragment = Countdown("Sports")
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).addToBackStack(null).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_developers -> {
                selectedFragment = DevelopersFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).addToBackStack(null).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false


    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        auth = FirebaseAuth.getInstance()
    }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser == null)
        {
            //If no user is logged in open sigin activity
            startActivity(Intent(this@HomeActivity, SignInActivity::class.java))

        }
        else
        {
            //if user is logged in  retrieve its data and save in static variables except Achievemets
            val uid = currentUser.uid

            DataRetrieve().playerDataRetrieve(uid)

            setSupportActionBar(findViewById(R.id.my_toolbar))

            imageView = findViewById(R.id.imageView)
            Glide.with(this@HomeActivity).load( PlayerData.udrAvatar).into(imageView!!)

            userName  = findViewById(R.id.textView6)
            userName!!.text = PlayerData.udrUserName

            points = findViewById(R.id.textView7)
            points!!.text = PlayerData.udrPoints.toString()

            bottomNav = findViewById(R.id.navigation)
            bottomNav.setOnNavigationItemSelectedListener(navListener)

            supportFragmentManager.beginTransaction().replace(R.id.homeFragment, HomeFragment()).commit()
            bottomNav.selectedItemId = navigation_home

        }


    }

}

