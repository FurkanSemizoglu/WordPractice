package com.furkansemizoglu.wordpractice.dayAdapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furkansemizoglu.wordpractice.WordCheckingActivity
import com.furkansemizoglu.wordpractice.databinding.DayRowBinding


class RecyclerViewAdapter ( val dayList : ArrayList<Day>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> (){


    class ViewHolder(val binding: DayRowBinding) : RecyclerView.ViewHolder(binding.root){
        var dayName : TextView= binding.dayText

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DayRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayName.text =dayList[position].DayName
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WordCheckingActivity::class.java)
            intent.putExtra("day",dayList[position].DayName.toString())
            intent.putExtra("dayNum",(position+1))
            Log.w("checkuuuuuuu",position.toString())
            holder.itemView.context.startActivity(intent)
        }
    }
}