package com.example.marik.urlslist.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marik.urlslist.model.ItemUrl

/**
 *  Adapter for urls list
 */
class UrlsListAdapter(var items: List<ItemUrl>): RecyclerView.Adapter<UrlViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        return UrlViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
         holder.bind(items[position])
    }

}