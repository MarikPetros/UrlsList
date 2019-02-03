package com.example.marik.urlslist.data

import android.arch.lifecycle.LiveData
import android.content.Context
import android.widget.Toast
import com.example.marik.urlslist.model.ItemUrl
import com.example.marik.urlslist.NetManager

/**
 *  Repository class used as a single source of data
 */
class UrlsRepository(
    val context: Context,
    val localDataSource: UrlsLocalRepository,
    val remoteDataSource: UrlsRemoteRepository
) {
    val netManager = NetManager(context)

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    //add new URL to list
    fun addItem(itemUrl: ItemUrl) {
        localDataSource.insert(itemUrl) {}

        if (netManager.isConnectedToInternet) {
            if (!isRequestInProgress) return

            isRequestInProgress = true
            localDataSource.insert(remoteDataSource.checkSingleUrl(itemUrl)) { isRequestInProgress = false }
        } else Toast.makeText(context, "Internet is not available!", Toast.LENGTH_LONG).show()
    }

    /**
     *  Function for refreshing the list
     */
    fun refreshAll() {
        if (netManager.isConnectedToInternet) {
            if (!isRequestInProgress) return

            isRequestInProgress = true
            localDataSource.updateAll(remoteDataSource.checkUrlsList(localDataSource.getList()))
            { isRequestInProgress = false }
        } else Toast.makeText(context, "Internet is not available!", Toast.LENGTH_LONG).show()
    }

    /**
     *  Function for searching the list for URl
     */
    fun searchUrl(string: String): LiveData<List<ItemUrl>> = localDataSource.findByName(string)

    //Function for getting the list of all items
    fun getAll() = localDataSource.getAll()

    // Functions for getting all items ordered by some criteria
    fun getAllByAvailability() = localDataSource.getAllByAvailability()

    fun getByNameAsc() = localDataSource.getByNameAsc()

    fun getByNameDesc() = localDataSource.getByNameDesc()

    fun getByResponseTimeAsc() = localDataSource.getByResponseTimeAsc()

    fun getByResponseTimeDesc() = localDataSource.getByResponseTimeDesc()

    // Delete item
    fun deleteItem(itemUrl: ItemUrl) {
        // TODO chishty stex Dialog sarqeln a
        if(isRequestInProgress) Toast.makeText(context, "Request for this URL is in progress!", Toast.LENGTH_LONG).show()
        localDataSource.delete(itemUrl)
    }

}