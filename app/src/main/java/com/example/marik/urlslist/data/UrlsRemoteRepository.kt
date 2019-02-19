package com.example.marik.urlslist.data

import android.arch.lifecycle.LiveData
import com.example.marik.urlslist.api.UrlAvailabilityService
import com.example.marik.urlslist.api.checkUrl
import com.example.marik.urlslist.model.ItemUrl
import java.util.concurrent.Executors

/**
 *  Class that handles network requests for URLs, evaluated
 *  and saved results
 */
object UrlsRemoteRepository {
    private val service: UrlAvailabilityService = UrlAvailabilityService.create()
    private val executor = Executors.newCachedThreadPool()
    private lateinit var newList: LiveData<List<ItemUrl>>

    /**
     *  Function for the single URL request
     */
    fun checkSingleUrl(itemUrl: ItemUrl): ItemUrl {
        executor.submit {
            synchronized(service) {
                checkUrl(service, itemUrl.name)
                { result -> // stex default resultn a galis?? ardyoq aydpes piti lini ?????? het a gnum checkUrl u nuyn vichakum galis a
                    itemUrl.isAvailable = result.isAvailable
                    itemUrl.responseTime = result.responseTime
                }
            }
        }
        return itemUrl
    }

    /**
     *  Function to handle requests for the list of URLs
     */
    fun checkUrlsList(list: List<ItemUrl>): List<ItemUrl> {
        executor.submit {
            list.forEach { itemUrl: ItemUrl -> checkSingleUrl(itemUrl) }
        }
        return list
    }
}