package com.example.marik.urlslist.data

import android.app.Dialog
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.marik.urlslist.NetManager
import com.example.marik.urlslist.R
import com.example.marik.urlslist.model.ItemUrl

/**
 *  Repository class used as a single source of data
 */
class UrlsRepository(
    val context: Context,
    private val localDataSource: UrlsLocalRepository,
    private val remoteDataSource: UrlsRemoteRepository
) {
    private val netManager = NetManager(context)

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    //add new URL to list
    fun addItem(itemUrl: ItemUrl) {
        localDataSource.insert(itemUrl) {}

        if (netManager.isConnectedToInternet) {
            if (isRequestInProgress) return

            isRequestInProgress = true
            localDataSource.insert(remoteDataSource.checkSingleUrl(itemUrl)) { isRequestInProgress = false }
        } else Toast.makeText(context, "Internet is not available!", Toast.LENGTH_LONG).show()
    }

    /**
     *  Function for refreshing the list
     */
    fun refreshAll() {
        if (netManager.isConnectedToInternet) {
            if (isRequestInProgress) {
                val oldList: MutableList<ItemUrl> = localDataSource.getList()
                localDataSource.updateAll(oldList.apply { forEach { item: ItemUrl -> item.isAvailable = null } })
                { Toast.makeText(context, "Request for URLs list is in progress!", Toast.LENGTH_LONG).show() }
                return
            }

            isRequestInProgress = true
            localDataSource.updateAll(remoteDataSource.checkUrlsList(localDataSource.getList()))
            { isRequestInProgress = false }
        } else Toast.makeText(context, "Internet is not available!", Toast.LENGTH_LONG).show()
    }

    /**
     *  Function for searching the list for URl
     */
    fun searchUrl(string: String): MutableList<ItemUrl> = localDataSource.findByName(string)

    //Function for getting the list of all items
    fun getAll(): LiveData<MutableList<ItemUrl>> = localDataSource.getAll()

    // Functions for getting all items ordered by some criteria
    fun getAllByAvailability(): LiveData<MutableList<ItemUrl>> = localDataSource.getAllByAvailability()

    fun getByNameAsc(): LiveData<MutableList<ItemUrl>> = localDataSource.getByNameAsc()

    fun getByNameDesc(): LiveData<MutableList<ItemUrl>> = localDataSource.getByNameDesc()

    fun getByResponseTimeAsc(): LiveData<MutableList<ItemUrl>> = localDataSource.getByResponseTimeAsc()

    fun getByResponseTimeDesc(): LiveData<MutableList<ItemUrl>> = localDataSource.getByResponseTimeDesc()

    // Delete item
    fun deleteItem(itemUrl: ItemUrl) {
        if (isRequestInProgress) {
            val dialog = DeletionDialog()
            if (dialog.isDeletable) {
                localDataSource.delete(itemUrl)
                return
            } else return
        }

        localDataSource.delete(itemUrl)
    }

    internal class DeletionDialog : DialogFragment() {
        var isDeletable: Boolean = true
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                // Use the Builder class for convenient dialog construction
                val builder = AlertDialog.Builder(it)
                builder.setMessage(getString(R.string.dialog_message))
                    .setPositiveButton(
                        R.string.delete
                    ) { _, _ ->
                        isDeletable = true
                    }
                    .setNegativeButton(
                        R.string.cancel
                    ) { _, _ ->
                        // User cancelled the dialog
                        isDeletable = false
                    }
                // Create the AlertDialog object and return it
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

}