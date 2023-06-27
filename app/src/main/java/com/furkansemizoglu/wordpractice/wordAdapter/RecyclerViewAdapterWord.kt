package com.furkansemizoglu.wordpractice.wordAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furkansemizoglu.wordpractice.databinding.CardLayoutBinding

class RecyclerViewAdapterWord(val wordList : ArrayList<Word>) : RecyclerView.Adapter<RecyclerViewAdapterWord.ViewHolder>() {
    class ViewHolder(val binding: CardLayoutBinding)  : RecyclerView.ViewHolder(binding.root){
        var wordName : TextView = binding.wordName


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.wordName.text = wordList[position].wordName
    }
}