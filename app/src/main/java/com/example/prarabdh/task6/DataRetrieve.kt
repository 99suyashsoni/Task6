package com.example.prarabdh.task6

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

internal class  DataRetrieve {

    val database = FirebaseDatabase.getInstance()
    
    fun playerDataRetrieve(userId: String)
    {

        val ref1 = database.getReference("Users").child(userId)
        ref1.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(
                    dataSnapshot: DataSnapshot) {

                PlayerData.udrUserId = userId
                PlayerData.udrAchievementsUnlocked = dataSnapshot.child("AchievementsUnlocked").value as String
                PlayerData.udrAvatar= dataSnapshot.child("Avatar").value as String
                PlayerData.udrCategoriesUnlocked= dataSnapshot.child("CategoriesUnlocked").value as String
                PlayerData.udrEmail= dataSnapshot.child("Email").value as String
                PlayerData.udrLoss= dataSnapshot.child("Loss").value as String
                PlayerData.udrPoints = dataSnapshot.child("Points").value.toString().toInt()
                PlayerData.udrUserName= dataSnapshot.child("Username").value as String
                PlayerData.udrWin= dataSnapshot.child("Win").value as String

                var iterable =dataSnapshot.child("Questions").children
                for(i in iterable ){

                    PlayerData.udrQuestionsAttempted.put(i.key.toString(),i.value.toString())
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

    }

    fun gameDescData(gameName: String){

        val ref2 = database.getReference("Categories").child(gameName)
        ref2.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                GameDescData.description = dataSnapshot.child("Description").value as String
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}