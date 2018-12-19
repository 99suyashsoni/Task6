package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HomeFragment: Fragment(){

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var recyclerView: RecyclerView? = null
    private val images =  intArrayOf(R.drawable.ic_dev)
    //val fActivity: FragmentActivity = this.activity!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.home_fragment,container,false)

        viewManager = GridLayoutManager(activity,2)
        viewAdapter = MyAdapter(images)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {

            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }
}