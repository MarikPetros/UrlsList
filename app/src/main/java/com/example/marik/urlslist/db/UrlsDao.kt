package com.example.marik.urlslist.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Used for access to data in DB
 */
@Dao
interface UrlsDao {
    @Query("SELECT * FROM itemurl")
    fun getAll(): LiveData<MutableList<ItemUrl>>

    @Query("SELECT * FROM itemurl")
    fun getList(): MutableList<ItemUrl>

    //Sorting selection methods
    @Query("SELECT * FROM itemurl ORDER BY name ASC")
    fun loadAllByNameAsc(): LiveData<MutableList<ItemUrl>>

    @Query("SELECT * FROM itemurl ORDER BY name DESC")
    fun loadAllByNameDesc(): LiveData<MutableList<ItemUrl>>

    @Query("SELECT * FROM itemurl ORDER BY isAvailable DESC")
    fun loadAllByAvailability(): LiveData<MutableList<ItemUrl>>

    @Query("SELECT * FROM itemurl ORDER BY responseTime ASC")
    fun loadAllByResponseTimeAsc(): LiveData<MutableList<ItemUrl>>

    @Query("SELECT * FROM itemurl ORDER BY responseTime DESC")
    fun loadAllByResponseTimeDesc(): LiveData<MutableList<ItemUrl>>


    //Searching for some url
    @Query("SELECT * FROM itemurl WHERE name LIKE :urlName")
    fun findByName(urlName: String): MutableList<ItemUrl>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUrl(item: ItemUrl)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(items: List<ItemUrl>)

    // Update all
    @Update
    fun updateAll(newList: MutableList<ItemUrl>)

    // Delete an item
    @Delete
    fun delete(item: ItemUrl)
}