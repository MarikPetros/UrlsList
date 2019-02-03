package com.example.marik.urlslist.data

import android.arch.lifecycle.LiveData
import com.example.marik.urlslist.model.ItemUrl
import com.example.marik.urlslist.model.UrlsDao
import java.util.concurrent.Executor

/**
 *  Class that handles DAO methods execution in Executor
 */
class UrlsLocalRepository(
    private val urlsDao: UrlsDao,
    private val executor: Executor
){
    /**
     *  insert an item in the database
     */
    fun insert(itemUrl: ItemUrl, insertFinished: ()-> Unit){
        executor.execute{
            urlsDao.insertUrl(itemUrl)
            insertFinished()
        }
    }

    /**
     *  update list of itemUrls in the database
     */
    fun updateAll(itemUrlsList: List<ItemUrl>, updateFinished: ()-> Unit){
        executor.execute{
            urlsDao.updateAll(itemUrlsList)
            updateFinished()
        }
    }

    /**
     *  get all items from the database as List<ItemUrl>
     */
    fun getList() = urlsDao.getList()

    /**
     *  get all items from the database as LiveData<List<ItemUrl>>
     */
    fun getAll() = urlsDao.getAll()

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by availability
      */
    fun getAllByAvailability() = urlsDao.loadAllByAvailability()

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by name ascending
     */
    fun getByNameAsc() = urlsDao.loadAllByNameAsc()

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by name descending
     */
    fun getByNameDesc() = urlsDao.loadAllByNameDesc()

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by response time ascending
     */
    fun getByResponseTimeAsc() = urlsDao.loadAllByResponseTimeAsc()

    /**
     *  get all items from the database as LiveData<List<ItemUrl>> ordered by response time descending
     */
    fun getByResponseTimeDesc() = urlsDao.loadAllByResponseTimeDesc()

    /**
     *  get search result from the database by name as LiveData<List<ItemUrl>>
     */
    fun findByName(urlName: String) = urlsDao.findByName(urlName)

    /**
     *  delete item's data from the database
     */
    fun delete(item: ItemUrl) = urlsDao.delete(item)
}
