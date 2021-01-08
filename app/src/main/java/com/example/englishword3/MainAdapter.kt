package com.example.englishword3

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainAdapter(requireContext: Context) :FragmentActivity(),FragmentCallback{

    val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity):
            FragmentStateAdapter(fragmentActivity){

        val items :List<Item> = listOf(Item(NounFragment(),"日本語"),Item(NounEnglishFragment(),"English"))

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment = items[position].fragment

        class Item(val fragment:Fragment,val title:String)
    }

    override fun japanese() {
    }

    override fun english() {
        viewPagerAdapter.items.firstOrNull { it.fragment is NounEnglishFragment }?.also {
            if (it.fragment is NounEnglishFragment)
                it.fragment.updateTasks()
        }
    }

}