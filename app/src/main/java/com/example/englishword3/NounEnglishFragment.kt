package com.example.englishword3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.englishword3.R

class NounEnglishFragment : Fragment() {

    private val nouneEnglishAdapter by lazy { NounEnglishAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_noun_english, container, false)
    }

    fun updateEnglish() {
        println("英語")

    }

    fun updateTasks() {
        initData()
    }

    fun initData() {

    }


}
