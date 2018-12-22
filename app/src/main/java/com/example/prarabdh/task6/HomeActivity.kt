package com.example.prarabdh.task6

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.example.prarabdh.task6.R.id.navigation_home


class HomeActivity : AppCompatActivity()
{
    private val email = "From firebase"
    private val player = Player(email)
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var selectedFragment: Fragment
        when (item.itemId) {
            navigation_home -> {
                selectedFragment = Countdown()
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

        val imageView:ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(player.avatar())

        val userName:TextView = findViewById(R.id.textView6)
        userName.text = player.userName()

        val points:TextView = findViewById(R.id.textView7)
        points.text = player.points().toString()

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val bottomNav: BottomNavigationView = findViewById(R.id.navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.homeFragment, Countdown()).commit()
            bottomNav.selectedItemId = navigation_home
        }

    }
}
