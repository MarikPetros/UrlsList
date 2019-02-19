package com.example.marik.urlslist.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.marik.urlslist.model.ItemUrl
import com.example.marik.urlslist.model.UrlsDao
import java.util.concurrent.Executor

/**
 *  Class that handles DAO methods execution in Executor
 */
class UrlsLocalRepository(
    private val urlsDao: UrlsDao,
    private val executor: Executor
) {
    /**
     *  insert an item in the database
     */
    fun insert(itemUrl: ItemUrl, insertFinished: () -> Unit) {
        executor.execute {
            urlsDao.insertUrl(itemUrl)
            insertFinished()
        }
    }

    /**
     *  update list of itemUrls in the database
     */
    fun updateAll(itemUrlsList: List<ItemUrl>, updateFinished: () -> Unit) {
        executor.execute {
            urlsDao.updateAll(itemUrlsList)
            updateFinished()
        }
    }

    private fun getAllItems(daoFunction: () -> List<ItemUrl>): List<ItemUrl>{
        var list: List<ItemUrl> = listOf()
        executor.execute {
            list = daoFunction()
        }
        return list
    }

    private fun getAllAsLiveData(daoFunction: () ->LiveData<List<ItemUrl>>): LiveData<List<ItemUrl>>{
        var list: LiveData<List<ItemUrl>> = MutableLiveData<List<ItemUrl>>()
        executor.execute {
            list = daoFunction()
        }
        return list
    }


    /**
     *  get all items from the database as List<ItemUrl>
     */
    fun getList(): List<ItemUrl> = getAllItems { urlsDao.getList() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>>
     */
    fun getAll(): LiveData<List<ItemUrl>> = getAllAsLiveData { urlsDao.getAll() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by availability
     */
    fun getAllByAvailability(): LiveData<List<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByAvailability() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by name ascending
     */
    fun getByNameAsc(): LiveData<List<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByNameAsc() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by name descending
     */
    fun getByNameDesc(): LiveData<List<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByNameDesc() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by response time ascending
     */
    fun getByResponseTimeAsc(): LiveData<List<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByResponseTimeAsc() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by response time descending
     */
    fun getByResponseTimeDesc(): LiveData<List<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByResponseTimeDesc() }

    /**
     *  get search result from the database by name as List<ItemUrl>
     */
    fun findByName(urlName: String): List<ItemUrl> = getAllItems { urlsDao.findByName(urlName) }

    /**
     *  delete item's data from the database
     */
    fun delete(item: ItemUrl) {
        executor.execute {
            urlsDao.delete(item)
        }
    }
}
