package com.example.prarabdh.task6.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.prarabdh.task6.DataRetrieve
import com.example.prarabdh.task6.dataModels.QuizData
import com.example.prarabdh.task6.ListenerObject
import com.example.prarabdh.task6.R
import com.example.prarabdh.task6.dataModels.PlayerData
import com.google.firebase.database.FirebaseDatabase


class MyAdapter(private val images: IntArray, private val names: Array<String>, private val listener1: ListenerObject, private val alertDialog: AlertDialog.Builder, private val toast: Toast): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        internal var button1: Button = view.findViewById(R.id.button1)
        internal var button2: Button = view.findViewById(R.id.button2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.game_images, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = images.size / 2

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.button1.setBackgroundResource(images[(2 * position)])
        holder.button2.setBackgroundResource(images[((2 * position) + 1)])
        holder.button1.text = names[(2 * position)]
        holder.button2.text = names[((2 * position) + 1)]
        holder.button1.setOnClickListener {

            if (PlayerData.udrCategoriesUnlocked[2*position] == '1') {
                Log.i("Button", "This quiz is unlocked")
                val listenerObject = ListenerObject()

                listenerObject.setCustomObjectListener(object : ListenerObject.Listener{
                    override fun onPartialDataChange() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataRecieved() {

                        QuizData.background = images[2*position]
                        QuizData.heading = names[2*position]
                        listener1.listener!!.onDataRecieved()
                    }

                })
                DataRetrieve().gameDescData(names[2*position], listenerObject)

            }
            else if (PlayerData.categoryPoints > 0)
            {
                alertDialog.setMessage("Do you want to unlock this category")
                        .setPositiveButton("YES") {dialog, which ->

                            Log.i("CategoryPoints","${PlayerData.categoryPoints}")
                            val count = PlayerData.udrCategoriesUnlocked.chars().filter { c -> c == '1'.toInt() }.count()
                            PlayerData.udrPoints -= PlayerData.pointsToUnlock[count.toInt()]
                            PlayerData.udrCategoriesUnlocked = PlayerData.udrCategoriesUnlocked.substring(0,2*position) + "1" + PlayerData.udrCategoriesUnlocked.substring(2*position+1)
                            PlayerData.categoryPoints = 0
                            FirebaseDatabase.getInstance().getReference("Users").child(PlayerData.udrUserId).child("CategoriesUnlocked").setValue(PlayerData.udrCategoriesUnlocked)
                            FirebaseDatabase.getInstance().getReference("Users").child(PlayerData.udrUserId).child("Points").setValue(PlayerData.udrPoints)
                            Log.i("CategoryPoints","${PlayerData.categoryPoints}")

                            val unlockedCategories = PlayerData.udrCategoriesUnlocked.toCharArray()
                            var noOfUnlocked = 0

                            for (item in unlockedCategories){
                                if (item == '1'){
                                    noOfUnlocked += 1
                                }
                            }

                            Log.d("unlocked","$noOfUnlocked")
                            val unlockPoints = PlayerData.pointsToUnlock.copyOfRange(noOfUnlocked, PlayerData.pointsToUnlock.size-1)
                            var sumPoints = 0

                            for (items in unlockPoints){
                                sumPoints += items
                                if (PlayerData.udrPoints >= sumPoints){
                                    PlayerData.categoryPoints += 1
                                }
                            }

                            Log.d("points","${PlayerData.categoryPoints}")

                        }
                        .setNegativeButton("NO") {dialog, which ->

                            dialog.cancel()
                        }
                        .show()

            }
            else
            {
                toast.show()
            }
        }

        holder.button2.setOnClickListener {

            if (PlayerData.udrCategoriesUnlocked[2*position+1] == '1') {
                Log.i("Button", "This quiz is unlocked")
                val listenerObject = ListenerObject()

                listenerObject.setCustomObjectListener(object : ListenerObject.Listener{
                    override fun onPartialDataChange() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataRecieved() {

                        QuizData.background = images[2*position+1]
                        QuizData.heading = names[2*position+1]
                        listener1.listener!!.onDataRecieved()
                    }

                })
                DataRetrieve().gameDescData(names[2*position+1], listenerObject)

            }
            else if (PlayerData.categoryPoints > 0)
            {

                alertDialog.setMessage("Do you want to unlock this category")
                        .setPositiveButton("YES") {dialog, which ->

                            Log.i("CategoryPoints","${PlayerData.categoryPoints}")
                            val count = PlayerData.udrCategoriesUnlocked.chars().filter { c -> c == '1'.toInt() }.count()
                            PlayerData.udrPoints -= PlayerData.pointsToUnlock[count.toInt()]
                            PlayerData.udrCategoriesUnlocked = PlayerData.udrCategoriesUnlocked.substring(0,2*position+1) + "1" + PlayerData.udrCategoriesUnlocked.substring(2*position+2)
                            PlayerData.categoryPoints = 0
                            FirebaseDatabase.getInstance().getReference("Users").child(PlayerData.udrUserId).child("CategoriesUnlocked").setValue(PlayerData.udrCategoriesUnlocked)
                            FirebaseDatabase.getInstance().getReference("Users").child(PlayerData.udrUserId).child("Points").setValue(PlayerData.udrPoints)
                            Log.i("CategoryPoints","${PlayerData.categoryPoints}")

                            val unlockedCategories = PlayerData.udrCategoriesUnlocked.toCharArray()
                            var noOfUnlocked = 0

                            for (item in unlockedCategories){
                                if (item == '1'){
                                    noOfUnlocked += 1
                                }
                            }

                            Log.d("unlocked","$noOfUnlocked")
                            val unlockPoints = PlayerData.pointsToUnlock.copyOfRange(noOfUnlocked, PlayerData.pointsToUnlock.size-1)
                            var sumPoints = 0

                            for (items in unlockPoints){
                                sumPoints += items
                                if (PlayerData.udrPoints >= sumPoints){
                                    PlayerData.categoryPoints += 1
                                }
                            }

                            Log.d("points","${PlayerData.categoryPoints}")

                        }
                        .setNegativeButton("NO") {dialog, which ->

                            dialog.cancel()
                        }
                        .show()

            }
            else
            {
                toast.show()
            }
        }

    }
}