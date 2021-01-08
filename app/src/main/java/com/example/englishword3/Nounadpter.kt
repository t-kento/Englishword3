package com.example.englishword3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.englishword3.R
import kotlinx.android.synthetic.main.item_list.view.*

class Nounadpter(private val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<AddWord>()

    var callback: NounadpterCallback? = null

    fun refresh(list: List<AddWord>) {
        items.apply {
            clear()
            addAll(this)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        holder.apply {
//            number_text.text = data.number_text
//            edit_text.text = data.edit_text
            deleteButton.setOnClickListener {
//                callback?.onClickDelete(data)
            }

        }
    }

    override fun getItemCount(): Int = items.size

    fun add(Japaneseword:AddWord) {
        items.add(Japaneseword)
        notifyDataSetChanged()

    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number_text = view.number_text
        val edit_text = view.edit_text
        val deleteButton = view.deleteButton
    }

    interface NounadpterCallback {
        fun onClickDelete(data: ListObject)
    }

}
