package com.furkansemizoglu.wordpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkansemizoglu.wordpractice.databinding.ActivityMainBinding
import com.furkansemizoglu.wordpractice.dayAdapter.Day
import com.furkansemizoglu.wordpractice.dayAdapter.RecyclerViewAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var dayNumber : Int = 0
    private var dayList = ArrayList<Day>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db = Firebase.firestore

        val dayInfo = hashMapOf<String,Any>()

        val recyclerView  = binding.recyclerView
        recyclerView.layoutManager =LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)

        val adapter = RecyclerViewAdapter(dayList)
        recyclerView.adapter = adapter

        db.collection("DayNum")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    dayNumber++
                    dayList.add(Day("Day $dayNumber"))
                    adapter.notifyDataSetChanged()
                }
            }


        binding.newDayClickButton.setOnClickListener {
            Toast.makeText(this@MainActivity,"clicked",Toast.LENGTH_SHORT).show()
            dayNumber++
            dayList.add(Day("Day $dayNumber"))
            adapter.notifyDataSetChanged()

            dayInfo.put("Day",dayNumber)
            db.collection("DayNum")
                .add(dayInfo)
                .addOnSuccessListener {
                    Toast.makeText(this@MainActivity,"Saved!",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@MainActivity,"db failed",Toast.LENGTH_LONG).show()
                }
        }

    }

/*
    fun newDayClicked(view : View){
        dayNumber++
        dayList.add(Day("Day $dayNumber"))
    }
    */

}