package com.example.timetracker.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R
import com.example.timetracker.adapter.TrackingAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var trackingAdapter: TrackingAdapter
    private lateinit var recycler_timer :RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setData()
    }


    fun setData(){
        try {
            recycler_timer = findViewById<RecyclerView>(R.id.recycler_timer);
            linearLayoutManager = LinearLayoutManager(this)
            recycler_timer.layoutManager = linearLayoutManager
            trackingAdapter = TrackingAdapter(this@MainActivity)
            recycler_timer.adapter = trackingAdapter

        }
         catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}