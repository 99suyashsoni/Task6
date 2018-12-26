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
import com.google.firebase.database.*


class HomeActivity : AppCompatActivity()
{
     // Initialised database and authentication variables and moved all UI items to updateUI method to Successfully
//    fill them with logged in users data
    // Removed all uses of PlayerDataRetrieveDataRetrieve.kt class with static variables of PlayerData Class starting with usr**
    // anytime u need usrers data access UserDataRetrieve class static variables
    //IMP note*****
    // Loops for retreiving Achivements were causing OutOfMemory error and thus was unable to retrieve Achievements
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var  bottomNav: BottomNavigationView

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
                selectedFragment = RandomFragment()
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
            //val uid = currentUser.uid

          //  PlayerDataRetrieve().dataRetrieve(uid)

            var uid = currentUser.uid
            database = FirebaseDatabase.getInstance().getReference("Users").child(uid)
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // val player= Player(uid)*****

                    PlayerData.udrUserId = uid
                    PlayerData.udrPoints = dataSnapshot.child("Total Points").value as String
                    PlayerData.udrEmail= dataSnapshot.child("Email-id").value as String
                    PlayerData.udrUserName= dataSnapshot.child("Username").value as String
                    PlayerData.udrAvtar= dataSnapshot.child("Avtar Img").value as String
                    PlayerData.udrWins= dataSnapshot.child("Wins").value as String
                    PlayerData.udrLosses= dataSnapshot.child("Losses").value as String

                    setSupportActionBar(findViewById(R.id.my_toolbar))

            val imageView:ImageView = findViewById(R.id.imageView)
            Glide.with(this@HomeActivity).load( PlayerData.udrAvtar).into(imageView)

            val userName:TextView = findViewById(R.id.textView6)
            userName.text = PlayerData.udrUserName

            val points:TextView = findViewById(R.id.textView7)
            points.text = PlayerData.udrPoints

            val bottomNav: BottomNavigationView = findViewById(R.id.navigation)
            bottomNav.setOnNavigationItemSelectedListener(navListener)

            supportFragmentManager.beginTransaction().replace(R.id.homeFragment, HomeFragment()).commit()
            bottomNav.selectedItemId = navigation_home

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })

        }


    }

}

