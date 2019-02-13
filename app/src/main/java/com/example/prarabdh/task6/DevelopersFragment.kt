package com.example.prarabdh.task6

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.developers_fragment.view.*


class DevelopersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.developers_fragment, container, false)
//        val bottomNav: BottomNavigationView = activity.findViewById(R.id.navigation)
//        bottomNav.selectedItemId = navigation_developers

        val nameAndImages = arrayListOf(Pair("Suyash Soni",R.drawable.suyash),Pair("Prarabdh Garg",R.drawable.prarabdh),
                Pair("Akshat Gupta",R.drawable.akshat),Pair("Ishita Aggrawal",R.drawable.ishita))
        val fnameAndImages = nameAndImages.shuffled()
        val (fname1,fimage1) = fnameAndImages[0]
        val (fname2,fimage2) = fnameAndImages[1]
        val (fname3,fimage3) = fnameAndImages[2]
        val (fname4,fimage4) = fnameAndImages[3]

        Glide.with(view).load(fimage1).into(view.img1)
        Glide.with(view).load(fimage2).into(view.img2)
        Glide.with(view).load(fimage3).into(view.img3)
        Glide.with(view).load(fimage4).into(view.img4)

        view.name1.text = fname1
        view.name2.text = fname2
        view.name3.text = fname3
        view.name4.text = fname4

        return view
    }
}