package com.example.prarabdh.task6

import android.provider.MediaStore.Video.VideoColumns.CATEGORY
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class  Player(userid: String)
{
    // No Longer Need this class
    val Userid=userid
    var points=0
    var email=""
    var username=""
    var avtar=""
    var wins=0
    var losses=0
//    var NUMBER_OF_ACHIVEMENTS=R.integer.Number_of_Achivements
//    var achivementsNames = Array<String>(NUMBER_OF_ACHIVEMENTS,{""})
//    var acivivementStatus = Array<Int>(NUMBER_OF_ACHIVEMENTS,{0})


    fun Player()
    {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users").child(Userid)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                points = dataSnapshot.child("Total Points").getValue() as Int
                email = dataSnapshot.child("Email-id").getValue() as String
                username = dataSnapshot.child("Username").getValue() as String
                avtar = dataSnapshot.child("Avtar Img").getValue() as String
                wins = dataSnapshot.child("Wins").getValue() as Int
                losses = dataSnapshot.child("Losses").getValue() as Int

// Disabled loops for retriving Achievements since they were causing OutOfMemory exception
//                val x=dataSnapshot.child("Achivements").children
//                var i=0;
//                for (contact in x)
//                {
//                    var y=contact.key;
//                    var z=contact.value
//                    achivementsNames[i]=y.toString()
//                    acivivementStatus[i]=x.toString() as Int
//                }
//

//                A1 = dataSnapshot.child("Achievements").child("A1").getValue() as String

            } override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun points(): Int
    {

        val playerPoints = points   //Retrieve from firebase
        return playerPoints
    }

    fun avatar(): String
    {

        val avatarImage = avtar //Retrieve from firebase
        return avatarImage
    }

    fun userName(): String
    {

        val name = username  //Retrieve from firebase
        return name
    }
}