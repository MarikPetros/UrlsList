package com.example.marik.urlslist.ui

import android.app.ListActivity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import com.example.marik.urlslist.R

/**
 *  Uses "singleTop" launch mode,
 *  does not create multiple instances of the searchable activity
 *  if the user will perform additional searches
 */
class SearchableActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }
    }

    private fun doMySearch(query: String) {

    }
}
