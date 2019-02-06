package com.example.marik.urlslist.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.marik.urlslist.data.UrlsRepository
import com.example.marik.urlslist.model.ItemUrl
import com.example.marik.urlslist.ui.MainActivity.Companion.AVAILABILITY
import com.example.marik.urlslist.ui.MainActivity.Companion.NAMES_ASC
import com.example.marik.urlslist.ui.MainActivity.Companion.NAMES_DESC
import com.example.marik.urlslist.ui.MainActivity.Companion.RESPONSE_TIME_ASC
import com.example.marik.urlslist.ui.MainActivity.Companion.RESPONSE_TIME_DESC
import com.example.marik.urlslist.ui.MainActivity.Companion.SAVED_SORT_TYPE

class UrlsListViewModel(val repository: UrlsRepository) : ViewModel() {

    var urlsList: LiveData<List<ItemUrl>> = repository.getAll()

    // add single url
    fun addUrl(itemUrl: ItemUrl) {
        repository.addItem(itemUrl)
    }

    // refresh all items
    fun refreshAll() {
        repository.refreshAll()

        urlsList = when (getSortState()) {
            NAMES_ASC -> repository.getByNameAsc()
            NAMES_DESC -> repository.getByNameDesc()
            AVAILABILITY -> repository.getAllByAvailability()
            RESPONSE_TIME_ASC -> repository.getByResponseTimeAsc()
            RESPONSE_TIME_DESC -> repository.getByResponseTimeDesc()
            else -> repository.getAll()

        }
    }

    private fun getSortState(): String? {
        val sPref: SharedPreferences = repository.context.getSharedPreferences(SAVED_SORT_TYPE, MODE_PRIVATE)
        return sPref.getString(SAVED_SORT_TYPE, "")
    }

    // search for an url
    fun searchUrl(string: String): List<ItemUrl> = repository.searchUrl(string)

    // functions to achieve all items
    fun getAll() {
        urlsList = repository.getAll()
    }

    fun getAllByAvailability() {
        urlsList = repository.getAllByAvailability()
    }

    fun getByNameAsc() {
        urlsList = repository.getByNameAsc()
    }

    fun getByNameDesc() {
        urlsList = repository.getByNameDesc()
    }

    fun getByResponseTimeAsc() {
        urlsList = repository.getByResponseTimeAsc()
    }

    fun getByResponseTimeDesc() {
        urlsList = repository.getByResponseTimeDesc()
    }


    // delete an item
    fun deleteItem(itemUrl: ItemUrl) = repository.deleteItem(itemUrl)
}





