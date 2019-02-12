package com.example.marik.urlslist.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.marik.urlslist.Injector
import com.example.marik.urlslist.R
import com.example.marik.urlslist.databinding.ActivityMainBinding
import com.example.marik.urlslist.model.ItemUrl

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: UrlsListViewModel
    private var urlsAdapter: UrlsListAdapter = UrlsListAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setSupportActionBar(toolbar)

        init()
        handleIntent(intent)
    }

    private fun init() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.executePendingBindings()

        viewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory(this))
            .get(UrlsListViewModel::class.java)
        binding.mainContent.viewModel = viewModel

        setRecyclerView()

        binding.fab.setOnClickListener {
            AddFragment.newInstance().show(supportFragmentManager, getString(R.string.fragment_tag))
        }
    }

    private fun setRecyclerView() {

        viewModel.urlsList.observe(this, Observer<List<ItemUrl>> { list ->
            list?.let {
                urlsAdapter = UrlsListAdapter(it)
            } ?: showEmptyList(true)
        })

        binding.mainContent.recyclerView.apply { this.adapter = urlsAdapter }
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
        val results = viewModel.searchUrl(query)
        urlsAdapter = UrlsListAdapter(results)
    }


    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.mainContent.emptyList.visibility = View.VISIBLE
            binding.mainContent.recyclerView.visibility = View.GONE
        } else {
            binding.mainContent.emptyList.visibility = View.GONE
            binding.mainContent.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.*/
        // Handle item selection
        return when (item.itemId) {
            R.id.search -> {
                onSearchRequested()
                true
            }
            R.id.sortByNameAsc -> {
                viewModel.getByNameAsc()// hop! kayni! viewModelic stacacy pti
                saveSortState(NAMES_ASC)
                true
            }
            R.id.sortByNameDesc -> {
                viewModel.getByNameDesc()
                saveSortState(NAMES_DESC)
                true
            }
            R.id.sortByAvailability -> {
                viewModel.getAllByAvailability()
                saveSortState(AVAILABILITY)
                true
            }
            R.id.sortByResponseTimeAsc -> {
                viewModel.getByResponseTimeAsc()
                saveSortState(RESPONSE_TIME_ASC)
                true
            }
            R.id.sortByResponseTimeDesc -> {
                viewModel.getByResponseTimeDesc()
                saveSortState(RESPONSE_TIME_DESC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveSortState(sortType: String) {
        val sPref: SharedPreferences = getSharedPreferences(SAVED_SORT_TYPE, MODE_PRIVATE)
        val editor = sPref.edit()
        editor.putString(SAVED_SORT_TYPE, sortType)
        editor.apply()
    }

    companion object {
        // Preference key
        const val SAVED_SORT_TYPE = "Saved type of list sorting"

        // Preference possible values
        const val NAMES_ASC = "Sorting by name ascending order"
        const val NAMES_DESC = "Sorting by name descending order"
        const val AVAILABILITY = "Sorting by availability order"
        const val RESPONSE_TIME_ASC = "Sorting by response time ascending order"
        const val RESPONSE_TIME_DESC = "Sorting by response time descending order"
    }
}
