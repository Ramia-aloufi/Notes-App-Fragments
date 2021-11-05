package com.example.notesappfragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import io.grpc.Context
import kotlinx.android.synthetic.main.notecell.view.*

class MyAdapter(var item:MutableList<Note>, var context1: mainview):RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {
    class ItemViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notecell,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var items = item[position]
        holder.itemView.apply {
            tv.text = items.note
            imageView.setOnClickListener { context1.preUpdate(items)}
            imageView2.setOnClickListener { context1.preDelete(items)}
    }
    }

    override fun getItemCount(): Int = item.size




}