package com.example.prarabdh.task6

class Player(email: String){

    fun points(): Int {

        val playerPoints = 3000   //Retrieve from firebase
        return playerPoints
    }

    fun avatar(): Int {

        val avatarImage = R.drawable.ic_profile  //Retrieve from firebase
        return avatarImage
    }

    fun userName(): String {

        val name = "User Name"  //Retrieve from firebase
        return name
    }
}