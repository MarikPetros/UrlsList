package com.example.marik.urlslist.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.marik.urlslist.data.UrlsRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory (private val repo: UrlsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrlsListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UrlsListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}