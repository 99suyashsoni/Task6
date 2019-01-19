package com.example.prarabdh.task6

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

internal class DataRetrieve {

    val database = FirebaseDatabase.getInstance()

    fun playerDataRetrieve(uId: String) {

        val ref1 = database.getReference("Users").child(uId)
        ref1.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(
                    dataSnapshot: DataSnapshot) {

                PlayerData.udrUserId = uId
                PlayerData.udrPoints = dataSnapshot.child("Total Points").value as String
                PlayerData.udrEmail = dataSnapshot.child("Email-id").value as String
                PlayerData.udrUserName = dataSnapshot.child("Username").value as String
                PlayerData.udrAvtar = dataSnapshot.child("Avtar Img").value as String
                PlayerData.udrWins = dataSnapshot.child("Wins").value as String
                PlayerData.udrLosses = dataSnapshot.child("Losses").value as String


            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

    }

    fun gameDescData(gameName: String) {

        val ref2 = database.getReference("Categories").child(gameName)
        ref2.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                GameDescData.description = dataSnapshot.child("Description").value as String
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}