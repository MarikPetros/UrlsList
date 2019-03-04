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
    fun updateAll(itemUrlsList: MutableList<ItemUrl>, updateFinished: () -> Unit) {
        executor.execute {
            urlsDao.updateAll(itemUrlsList)
            updateFinished()
        }
    }

    private fun getAllItems(daoFunction: () -> MutableList<ItemUrl>): MutableList<ItemUrl>{
        var list: MutableList<ItemUrl> = mutableListOf()
        executor.execute {
            list = daoFunction()
        }
        return list
    }

    private fun getAllAsLiveData(daoFunction: () ->LiveData<MutableList<ItemUrl>>): LiveData<MutableList<ItemUrl>>{
        var list: LiveData<MutableList<ItemUrl>> = MutableLiveData<MutableList<ItemUrl>>()
        executor.execute {
            list = daoFunction()
        }
        return list
    }


    /**
     *  get all items from the database as List<ItemUrl>
     */
    fun getList(): MutableList<ItemUrl> = getAllItems { urlsDao.getList() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>>
     */
    fun getAll(): LiveData<MutableList<ItemUrl>> = getAllAsLiveData { urlsDao.getAll() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by availability
     */
    fun getAllByAvailability(): LiveData<MutableList<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByAvailability() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by name ascending
     */
    fun getByNameAsc(): LiveData<MutableList<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByNameAsc() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by name descending
     */
    fun getByNameDesc(): LiveData<MutableList<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByNameDesc() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by response time ascending
     */
    fun getByResponseTimeAsc(): LiveData<MutableList<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByResponseTimeAsc() }

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by response time descending
     */
    fun getByResponseTimeDesc(): LiveData<MutableList<ItemUrl>> = getAllAsLiveData { urlsDao.loadAllByResponseTimeDesc() }

    /**
     *  get search result from the database by name as List<ItemUrl>
     */
    fun findByName(urlName: String): MutableList<ItemUrl> = getAllItems { urlsDao.findByName(urlName) }

    /**
     *  delete item's data from the database
     */
    fun delete(item: ItemUrl) {
        executor.execute {
            urlsDao.delete(item)
        }
    }
}
