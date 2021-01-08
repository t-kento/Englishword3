package com.example.englishword3

import android.app.AlertDialog
import android.media.CamcorderProfile.get
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishword3.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_noun.*
import kotlinx.android.synthetic.main.fragment_noun_english.*
import kotlinx.android.synthetic.main.fragment_noun.swipeRefreshLayout as swipeRefreshLayout1
import java.util.*

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class NounFragment : Fragment() {

    private val nounAdapter by lazy { Nounadpter(context!!) }
    private var wordId = "${System.currentTimeMillis()}"


    private var fragmentCallback: FragmentCallback? = null
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_noun, container, false)
    }


    fun updateJapnese() {
        println("日本語")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        wordId = arguments?.getString(KEY_WORD_ID) ?: ""
        initLayout()
        initData()
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        fab.setOnClickListener {
            showInputNounDialog()
        }
        re.setOnClickListener {
            println("押されました")
            activity?.onBackPressed()
        }
    }


    private fun initRecyclerView() {
        nounAdapter.callback = object : Nounadpter.NounadpterCallback {
            override fun onClickDelete(data: ListObject) {
                deleteWord()
            }

        }
        memoRecyclerView.apply {
            adapter = nounAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun showInputNounDialog() {
        val inflater = layoutInflater
        val inflate_view = inflater.inflate(R.layout.custom_view, null)

        val japanese_nounEdit = inflate_view.findViewById(R.id.japanese_text) as EditText
        val english_nounEdit = inflate_view.findViewById(R.id.english_text) as EditText

        AlertDialog.Builder(requireContext())
            .setTitle("単語登録")
            .setMessage("単語を入力してください")
            .setView(inflate_view)
            .setPositiveButton("登録") { _, _ ->
                addJapaneseNoune(japanese_nounEdit.text.toString())
                addEnglishNoune(english_nounEdit.text.toString())
                Toast.makeText(context, "登録完了", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("キャンセル") { _, _ -> }
            .show()
    }

    private fun addJapaneseNoune(japanesenoun: String) {
        println("日本${japanesenoun}")
        FirebaseFirestore.getInstance()
            .collection("Japanesewword")
            .add(AddWord().apply {
                Japaneseword = japanesenoun
                wordId = wordId
            })
    }

    private fun addEnglishNoune(noun: String) {
        println("英${noun}")
        FirebaseFirestore.getInstance()
            .collection("Englishwword")
            .add(AddWord().apply {
                Englishword = noun
                wordId = wordId
            })
    }

    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("word")
            .whereEqualTo(AddWord::wordId.name, wordId)
            .orderBy(AddWord::createdAt.name)
            .get()
            .addOnCompleteListener {
                swipeRefreshLayout1.isRefreshing = false
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                var date = Date()
                it.result?.toObjects(AddWord::class.java)?.also { word ->
                    nounAdapter.refresh(word)
                    date = word.lastOrNull()?.createdAt ?: Date()
                }
                initSubscribe(date)
            }
    }

    private fun initSubscribe(lastCreatedAt: Date) {
          db.collection("Japaneseword")
              .whereEqualTo(AddWord::wordId.name,wordId)
              .orderBy(AddWord::createdAt.name,Query.Direction.DESCENDING)
              .whereGreaterThan(AddWord::createdAt.name,lastCreatedAt)
              .limit(1L)
              .addSnapshotListener{snapshot,firebaseFirestoreException->
                  if(firebaseFirestoreException!=null){
                      firebaseFirestoreException.printStackTrace()
                      return@addSnapshotListener
                  }
                  snapshot?.toObjects(AddWord::class.java)?.firstOrNull()?.also{
                      nounAdapter.add(it)
                  }
              }
          }


    private fun deleteWord() {
        initData()
    }


    companion object {
        const val KEY_WORD_ID = "key_word_id"
    }
}

