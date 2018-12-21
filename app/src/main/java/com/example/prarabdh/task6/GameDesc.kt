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
    private var gameDescrip: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_desc,container,false)

        singlePlayer = HomeActivity().findViewById(R.id.button2)
        multiPLayer = HomeActivity().findViewById(R.id.button3)
        gameName = HomeActivity().findViewById(R.id.textView8)
        gameDescrip = HomeActivity().findViewById(R.id.textView9)

        gameName!!.text = ""
        gameDescrip!!.text = ""
        view.setBackgroundResource(0)

        singlePlayer!!.setOnClickListener {

        }

        multiPLayer!!.setOnClickListener {

        }

        return view
    }
}