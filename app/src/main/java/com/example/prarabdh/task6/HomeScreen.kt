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
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
               // message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false


    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_test)

        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var fragment=FragmentMainQuiz()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.Frame, fragment)
        transaction.commit()

    }
}
