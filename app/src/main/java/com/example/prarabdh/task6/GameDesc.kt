package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class GameDesc: Fragment(){

    private var singlePlayer: Button? = null
    private var multiPLayer: Button? = null
    private var gameName: TextView? = null
    private var gameDescription: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_desc,container,false)

        singlePlayer = view.findViewById(R.id.button2)
        multiPLayer = view.findViewById(R.id.button3)
        gameName = view.findViewById(R.id.textView8)
        gameDescription = view.findViewById(R.id.textView9)

        gameName!!.text = "Game 1"
        gameDescription!!.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore " +
                             "magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."

        view.setBackgroundResource(R.drawable.blue_wall)

        singlePlayer!!.setOnClickListener {

        }

        multiPLayer!!.setOnClickListener {

        }

        return view
    }
}