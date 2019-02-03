package com.example.marik.urlslist

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.marik.urlslist.model.UrlsDatabase
import com.example.marik.urlslist.data.UrlsLocalRepository
import com.example.marik.urlslist.data.UrlsRemoteRepository
import com.example.marik.urlslist.data.UrlsRepository
import com.example.marik.urlslist.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injector {
    /**
     * Creates an instance of [UrlsLocalRepository] based on the database DAO.
     */
    private fun provideLocalRepository(context: Context): UrlsLocalRepository {
        val database = UrlsDatabase.getInstance(context)
        return UrlsLocalRepository(database.urlDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [UrlsRepository] based on the [UrlsRemoteRepository] and a
     * [UrlsLocalRepository]
     */
    private fun provideUrlsRepository(context: Context): UrlsRepository {
        return UrlsRepository(context, provideLocalRepository(context), UrlsRemoteRepository)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideUrlsRepository(context))
    }

}