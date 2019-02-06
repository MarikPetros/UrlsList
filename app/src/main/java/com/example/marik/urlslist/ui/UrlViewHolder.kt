package com.example.marik.urlslist.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marik.urlslist.databinding.UrlsListItemBinding
import com.example.marik.urlslist.model.ItemUrl

/**
 *  View holder for a [ItemUrl] RecyclerView list item
 */
class UrlViewHolder(private var binding: UrlsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        // open the site with the url
        binding.root.setOnClickListener {
            binding.itemUrl?.name.let { name ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(name))
                itemView.context.startActivity(intent)
            }
        }

        // make "delete" image visible on long click
        binding.root.setOnLongClickListener {
            if (binding.actionUrlRemove.visibility != (View.VISIBLE)) {
                binding.actionUrlRemove.visibility = View.VISIBLE
            }else binding.actionUrlRemove.visibility = ( View.GONE)

            true
        }
    }

    fun bind(item: ItemUrl){
        binding.itemUrl = item
    }

    companion object {
        fun create(parent: ViewGroup): UrlViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = UrlsListItemBinding.inflate(layoutInflater, parent, false)
            return UrlViewHolder(binding)
        }
    }
}