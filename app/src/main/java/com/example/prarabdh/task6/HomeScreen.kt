package com.example.prarabdh.task6

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_screen.*
import android.R.attr.fragment



class HomeScreen : AppCompatActivity()
{

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                //message.setText(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
               // message.setText(R.string.title_leaderboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_random -> {
                //message.setText(R.string.title_random)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_developers -> {
                //message.setText(R.string.title_dev)
                return@OnNavigationItemSelectedListener true
            }
        }
        false


    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_fragment_main_quiz)

        var fragment=FragmentMainQuiz()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.Frame, fragment)
        transaction.commit()

    }
}
