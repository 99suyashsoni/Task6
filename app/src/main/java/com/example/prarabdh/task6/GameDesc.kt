package com.example.prarabdh.task6.fragmentClasses

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.prarabdh.task6.Countdown
import com.example.prarabdh.task6.R
import com.example.prarabdh.task6.dataModels.QuizData


class GameDesc : Fragment() {

    private var singlePlayer: Button? = null
    private var multiPLayer: Button? = null
    private var gameName: TextView? = null
    private var gameDescription: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_desc, container, false)

        singlePlayer = view.findViewById(R.id.button2)
        multiPLayer = view.findViewById(R.id.button3)
        gameName = view.findViewById(R.id.textView8)
        gameDescription = view.findViewById(R.id.textView9)

        gameName!!.text = QuizData.heading
        gameDescription!!.text = QuizData.description
        view.setBackgroundResource(QuizData.background)

        singlePlayer!!.setOnClickListener {
            //gameName!!.setText("Sports")
            activity?.supportFragmentManager?.beginTransaction()!!.replace(R.id.homeFragment, Countdown(gameName!!.text as String?)).commit()
        }

        multiPLayer!!.setOnClickListener {
            Toast.makeText(context,"Coming Soon",Toast.LENGTH_LONG).show()
        }

        return view
    }
}