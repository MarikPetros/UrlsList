package com.example.marik.urlslist.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.marik.urlslist.data.UrlsRepository
import com.example.marik.urlslist.model.ItemUrl

class UrlsListViewModel(val repository: UrlsRepository) : ViewModel() {

    var urlsList: LiveData<List<ItemUrl>> = repository.getAll()

    // add single url
    fun addUrl(itemUrl: ItemUrl) {
        repository.addItem(itemUrl)
    }

    // refresh all items
    fun refreshAll() = repository.refreshAll()

    // search for an url
    fun searchUrl(string: String): LiveData<List<ItemUrl>> = repository.searchUrl(string)

    // functions to achieve all items
    fun getAll() { urlsList = repository.getAll() }

    fun getAllByAvailability() { urlsList = repository.getAllByAvailability() }

    fun getByNameAsc() { urlsList = repository.getByNameAsc()}

    fun getByNameDesc()  { urlsList = repository.getByNameDesc()}

    fun getByResponseTimeAsc()  { urlsList = repository.getByResponseTimeAsc()}

    fun getByResponseTimeDesc()  { urlsList = repository.getByResponseTimeDesc()}


    // delete an item
    fun deleteItem(itemUrl: ItemUrl) = repository.deleteItem(itemUrl)
}





