package com.example.marik.urlslist.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marik.urlslist.model.ItemUrl


/**
 *  Adapter for urls list
 */
class UrlsListAdapter : RecyclerView.Adapter<UrlViewHolder>() {
    private var items: List<ItemUrl> = listOf()

    lateinit var mOnItemDeleteListener: OnItemDeleteListener

    fun setOnItemDeleteListener(listener: OnItemDeleteListener) {
        mOnItemDeleteListener = listener
    }

    private val mOnDeleteListener = object : UrlViewHolder.OnDeleteListener {
      override  fun delete(adapterPosition: Int) {
            mOnItemDeleteListener.onItemDelete( items[adapterPosition] )
        }
    }

    interface OnItemDeleteListener {
        fun onItemDelete(item: ItemUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val holder = UrlViewHolder.create(parent)
        holder.setOnDeleteListener(mOnDeleteListener)
        return holder
    }

    fun setList(list: List<ItemUrl>) {
        items = list
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
        holder.bind(items[position])
    }

}