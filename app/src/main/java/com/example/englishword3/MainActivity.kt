package com.example.englishword3

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.englishword3.AddWord
import com.example.englishword3.NounActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var roomId : ListenerRegistration? =null
    private val items = mutableListOf<AddWord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val key = "word"
        val animal = "芝犬"
        val Eanimal= "shiba"
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference(key)
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("word")


        text_view1.setOnClickListener {
            val noun = Intent(application, NounActivity::class.java)
            startActivity(noun)
        }
        text_view2.setOnClickListener {
            println("動詞")
            db.collection("w")
                    .add(AddWord().apply {
                        Japaneseword = animal
                        Englishword= Eanimal
                    })
        }

        text_view3.setOnClickListener {
                     initData()
                        }
        }
    private fun initData() {
        FirebaseFirestore.getInstance()
                .collection("word")
                .get()
                .addOnCompleteListener {
                    println(items)
                    if (!it.isSuccessful)
                        return@addOnCompleteListener
                    it.result?.toObjects(AddWord::class.java)?.also {
                    }
                }
    }

//    private fun  data(List<AddWord>,position:Int) {
//        val data = items[position]
//        println("${data.Englishword}")
//    }

//            val docRef =db.collection("word").document("Japaneseword")
//            docRef.get()
//                    .addOnSuccessListener { word ->
//                        if(word != null){
//                            Log.d("MainActivity","Cached document data: ${word.Japaneseword}")
//                            println("どうなっているのか：${Japaneseword}")
//                        }else{
//                            Log.d("MainActivity","No such document")
//                            println("ミス")
//                        }
//                    }
//                    .addOnFailureListener{exception ->
//                        Log.d("MainActivity","get failed with",exception)
//                    }
//
//
//        }
    }

