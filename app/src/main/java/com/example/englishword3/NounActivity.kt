package com.example.englishword3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.englishword3.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_noun.*
import kotlinx.android.synthetic.main.fragment_noun.*


class NounActivity : FragmentActivity(),FragmentCallback{

    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noun)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initViewPager2()
        initTabLayout()
    }


    private fun initViewPager2() {
        viewPager2.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(tabLayout, viewPager2) { tab: TabLayout.Tab, position: Int ->
            tab.text = viewPagerAdapter.items[position].language
        }.attach()
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity):
            FragmentStateAdapter(fragmentActivity){

        val items:List<Item> = listOf(Item(NounFragment(),"日本語"),Item(NounEnglishFragment(),"English"))

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment =items[position].fragment
        class Item(val fragment:Fragment,val language:String)
    }

    override fun japanese(){
        viewPagerAdapter.items.firstOrNull{it.fragment is NounFragment}?.also{
            if(it.fragment is NounFragment)
                it.fragment.updateJapnese()
        }

    }

    override fun english() {
        viewPagerAdapter.items.firstOrNull{it.fragment is NounEnglishFragment}?.also{
            if(it.fragment is NounEnglishFragment)
                it.fragment.updateEnglish()
        }

    }


}
