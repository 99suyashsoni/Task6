package com.example.prarabdh.task6

import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

internal class  DataRetrieve {

    val database = FirebaseDatabase.getInstance()
    
    fun playerDataRetrieve(userId: String, l1: ListenerObject)
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

                val iterable =dataSnapshot.child("Questions").children
                for(i in iterable ){

                    PlayerData.udrQuestionsAttempted[i.key.toString()] = i.value.toString()
                }

                l1.listener!!.onDataRecieved()
            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

    }

    fun gameDescData(gameName: String, l2: ListenerObject){

        val ref2 = database.getReference("Categories").child(gameName)
        ref2.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                GameDescData.description = dataSnapshot.child("Description").value as String

                l2.listener!!.onDataRecieved()
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }

    fun pointsUnlockData(l3: ListenerObject){

        database.getReference("UnlockPoints").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dsp: DatabaseError) {

            }

            override fun onDataChange(dsp: DataSnapshot) {

                val x = dsp.value.toString()
                PlayerData.pointsToUnlock = x.split(' ').map { it.toInt() }.toIntArray()

                l3.listener!!.onDataRecieved()

            }
        })


    }

    fun getCategoryNames(l4: ListenerObject){

        database.getReference("Categories").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dsp: DataSnapshot) {

                for ((j, d) in dsp.children.withIndex()) {

                    GameDescData.names[j] = d.key.toString()

                }

                l4.listener!!.onDataRecieved()
            }

            override fun onCancelled(dsp: DatabaseError) {
            }
        })


    }

    // for leaderboard
    fun getLeadorboardData(l5: ListenerObject,l6: ListenerObject){

        database.getReference("Users").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    var model: LeaderboardDataModel
                Log.d("LeaderboardFragment", "Size: ${dataSnapshot.children.count()}")

                for (dsp in dataSnapshot.children) {
                    var uname = dsp.child("Username").value!!.toString()
                    var points = dsp.child("Points").value!!.toString().toInt()
                    var avatar = dsp.child("Avatar").value!!.toString()

                    var model = LeaderboardDataModel(points, avatar, uname)

                    Log.d("LeaderboardFragment", "C2")

                    PlayerData.datasetLeaderboard.add(model)
                    PlayerData.datasetLeaderboard.sortByDescending { it.Points }
                }


l5.listener!!.onDataRecieved()
            }


            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
                l6.listener!!.onDataRecieved()
            }
        })

    }
}