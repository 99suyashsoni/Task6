package com.example.prarabdh.task6

class ListenerObject {
    interface Listener {
        fun onDataRecieved()
        fun onPartialDataChange()
    }

    var listener: Listener? = null

    fun setCustomObjectListener(listener: Listener) {

        this.listener = listener
    }
}