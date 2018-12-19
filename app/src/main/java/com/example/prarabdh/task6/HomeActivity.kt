package com.example.prarabdh.task6

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_screen.*
import android.R.attr.fragment
import android.net.wifi.hotspot2.pps.HomeSp
import android.support.v4.app.Fragment
import com.example.prarabdh.task6.R.id.navigation_home


class HomeActivity : AppCompatActivity()
{

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
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
                selectedFragment = LeaderboardFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_random -> {
                selectedFragment = RandomFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_developers -> {
                selectedFragment = DevelopersFragment()
                supportFragmentManager.beginTransaction().replace(R.id.homeFragment, selectedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false


    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        var bottomNav: BottomNavigationView = findViewById(R.id.navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.homeFragment, HomeFragment()).commit()
            bottomNav.selectedItemId = navigation_home
        }

    }
}
