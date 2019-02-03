package com.example.marik.urlslist.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.marik.urlslist.db.Converters

@Database(entities = [ItemUrl::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UrlsDatabase : RoomDatabase() {

    abstract fun urlDao(): UrlsDao

    companion object {

        @Volatile
        private var INSTANCE: UrlsDatabase? = null

        fun getInstance(context: Context): UrlsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UrlsDatabase::class.java, "Urls.db")
                .build()

    }
}

