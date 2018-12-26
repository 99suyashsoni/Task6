package com.example.prarabdh.task6

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

internal class  PlayerDataRetrieve {

    fun dataRetrieve(uId: String) {

        val database = FirebaseDatabase.getInstance().getReference("Users").child(uId)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                PlayerData.udrUserId = uId
                PlayerData.udrPoints = dataSnapshot.child("Total Points").value as String
                PlayerData.udrEmail= dataSnapshot.child("Email-id").value as String
                PlayerData.udrUserName= dataSnapshot.child("Username").value as String
                PlayerData.udrAvtar= dataSnapshot.child("Avtar Img").value as String
                PlayerData.udrWins= dataSnapshot.child("Wins").value as String
                PlayerData.udrLosses= dataSnapshot.child("Losses").value as String
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

    }
}