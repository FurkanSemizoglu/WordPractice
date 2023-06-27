package com.furkansemizoglu.wordpractice

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.furkansemizoglu.wordpractice.databinding.ActivityWordCheckingBinding
import com.furkansemizoglu.wordpractice.wordAdapter.RecyclerViewAdapterWord
import com.furkansemizoglu.wordpractice.wordAdapter.Word
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class WordCheckingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWordCheckingBinding
    private var dayNumPosition = 0
    private var wordList = ArrayList<Word>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordCheckingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle = intent.extras

        if (bundle?.getInt("dayNum") != null){
            dayNumPosition = bundle.getInt("dayNum")
            Log.w("checkii",dayNumPosition.toString())
        }
        if (bundle?.getString("day") != null){
            binding.exampleText.text = bundle.getString("day").toString()
            Log.w("checkiiiii",bundle.getString("day").toString())
        }

        val db = Firebase.firestore
        val words = hashMapOf<String,Any>()

      //  wordList.add(Word("Hallo"))
      //  wordList.add(Word("Tchüss"))
        val recyclerView = binding.wordRecyclerView
        recyclerView.layoutManager = GridLayoutManager(this,2)
        val adapter = RecyclerViewAdapterWord(wordList)
        recyclerView.adapter = adapter

        binding.newWordButton.setOnClickListener {
            showAlertWithTextInputLayout(this@WordCheckingActivity,adapter)
         //   adapter.notifyDataSetChanged()
         //   listWordData(adapter)
        }

        db.collection("DayNum")
            .get()
            .addOnSuccessListener {
                for(documentt in it){
                    if (documentt.get("Day").toString().equals(dayNumPosition.toString())){
                        db.collection("DayNum").document(documentt.id)
                            .collection("words")
                            .get()
                            .addOnSuccessListener {
                                for(wordDocument in it){
                                  wordList.add(Word(wordDocument.get("word").toString()))
                                    adapter.notifyDataSetChanged()
                                }
                            }
                    }
                }
            }




    }

    private fun listWordData(adapter: RecyclerViewAdapterWord){
        val db = Firebase.firestore

        db.collection("DayNum")
            .get()
            .addOnSuccessListener {
                for(documentt in it){
                    if (documentt.get("Day").toString().equals(dayNumPosition.toString())){
                        db.collection("DayNum").document(documentt.id)
                            .collection("words")
                            .get()
                            .addOnSuccessListener {
                                for(wordDocument in it){
                                    wordList.add(Word(wordDocument.get("word").toString()))
                                    adapter.notifyDataSetChanged()
                                }
                            }
                    }
                }
            }
    }

    private fun showAlertWithTextInputLayout(context: Context , adapter: RecyclerViewAdapterWord) {
        val textInputLayout = TextInputLayout(context)

        textInputLayout.setPadding(

            resources.getDimensionPixelOffset(R.dimen.app_icon_size), // if you look at android alert_dialog.xml, you will see the message textview have margin 14dp and padding 5dp. This is the reason why I use 19 here
            0,
            resources.getDimensionPixelOffset(R.dimen.app_icon_size),
            0
        )
        val input = EditText(context)

        textInputLayout.addView(input)

        val words = hashMapOf<String,Any>()

        val alert = AlertDialog.Builder(context)
            .setTitle("New Word")
            .setView(textInputLayout)
            .setPositiveButton("Submit", ) { dialog, _ ->

                val db = Firebase.firestore
                Toast.makeText(this@WordCheckingActivity,dialog.toString(),Toast.LENGTH_SHORT).show()
                words.put("word",input.text.toString())

                 db.collection("DayNum")
                     .get()
                     .addOnSuccessListener {
                         for (documentt in it) {
                             if (documentt.get("Day").toString().equals(dayNumPosition.toString())) {
                                 db.collection("DayNum").document(documentt.id)
                                     .collection("words")
                                     .add(words)
                                     .addOnSuccessListener {
                                         wordList.add(Word(words.get("word").toString()))
                                         adapter.notifyDataSetChanged()
                                     }
                             }

                         }
                     }

            }

        alert.show()


    }
}