package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.prarabdh.task6.adapters.MyAdapter
import com.example.prarabdh.task6.dataModels.QuizData
import com.example.prarabdh.task6.dataModels.PlayerData
import com.example.prarabdh.task6.fragmentClasses.GameDesc

class HomeFragment : Fragment() {

    private var check1: Boolean = false
    private var check2: Boolean = false
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var recyclerView: RecyclerView? = null
    private val images = intArrayOf(R.drawable.blue1_wall, R.drawable.blue2_wall, R.drawable.blue_wall, R.drawable.green_wall,
            R.drawable.pink_wall, R.drawable.purple1_wall, R.drawable.purple2_wall, R.drawable.purple_wall, R.drawable.red_wall,
            R.drawable.yellow_wall)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val listenerObject3 = ListenerObject()

        val alertDialog = AlertDialog.Builder(context!!)
        val toast = Toast.makeText(context,"Category is locked", Toast.LENGTH_SHORT)

        listenerObject3.setCustomObjectListener(object : ListenerObject.Listener{
            override fun onPartialDataChange() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataRecieved() {
                Log.d("Activity", "Activity2 $activity!!")
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.homeFragment, GameDesc()).addToBackStack(null).commit()
            }

        })


        /**
         * Listener1 Object is called after all categories are retrived from firebase
         * Once all categories are retrived, we display the tiles corosponding to all the categories
         * But before displaying the tiles, we also make sure that the points to Unlock data is completely fetched
         * This helps in preventing cases where the player wants to unlock a new category but the information about the points required to unlock it is still not fetched
         */

        val listenerObject1 = ListenerObject()

        listenerObject1.setCustomObjectListener(object : ListenerObject.Listener {
            override fun onPartialDataChange() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataRecieved() {

                check1 = true
                if(check1 && check2)
                {
                    val unlockedCategories = PlayerData.udrCategoriesUnlocked.toCharArray()
                    var noOfUnlocked = 0

                    for (item in unlockedCategories){
                        if (item == '1'){
                            noOfUnlocked += 1
                        }
                    }

                    Log.d("unlocked","$noOfUnlocked")
                    val unlockPoints = PlayerData.pointsToUnlock.copyOfRange(noOfUnlocked,PlayerData.pointsToUnlock.size-1)
                    var sumPoints = 0

                    for (items in unlockPoints){
                        sumPoints += items
                        if (PlayerData.udrPoints >= sumPoints){
                            PlayerData.categoryPoints += 1
                        }
                    }

                    viewManager = LinearLayoutManager(activity)
                    viewAdapter = MyAdapter(images, QuizData.names, listenerObject3, alertDialog, toast!!)
                    recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {

                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                    check1 = false
                }

            }
        })

        /**
         * Listener Object 2 is called ehen data retrival about the points to unlock each category has been fetched completely
         */

        val listenerObject2 = ListenerObject()

        listenerObject2.setCustomObjectListener(object : ListenerObject.Listener {
            override fun onPartialDataChange() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataRecieved() {

                check2 = true
                if(check1 && check2)
                {
                    val unlockedCategories = PlayerData.udrCategoriesUnlocked.toCharArray()
                    var noOfUnlocked = 0

                    for (item in unlockedCategories){
                        if (item == '1'){
                            noOfUnlocked += 1
                        }
                    }

                    val unlockPoints = PlayerData.pointsToUnlock.copyOfRange(noOfUnlocked,PlayerData.pointsToUnlock.size-1)

                    var sumPoints = 0

                    for (items in unlockPoints){
                        sumPoints += items
                        if (PlayerData.udrPoints >= sumPoints){
                            PlayerData.categoryPoints += 1
                        }
                    }

                    viewManager = LinearLayoutManager(activity)
                    viewAdapter = MyAdapter(images, QuizData.names, listenerObject3, alertDialog, toast!!)
                    recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {

                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                    check1 = false
                }

            }
        })

        DataRetrieve().getCategoryNames(listenerObject1)
        DataRetrieve().pointsUnlockData(listenerObject2)

        return view
    }
}
