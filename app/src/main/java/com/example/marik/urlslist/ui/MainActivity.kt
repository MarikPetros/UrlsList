package com.example.marik.urlslist.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.marik.urlslist.Injector
import com.example.marik.urlslist.R
import com.example.marik.urlslist.databinding.ContentMainBinding
import com.example.marik.urlslist.model.ItemUrl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ContentMainBinding
    lateinit var viewModel: UrlsListViewModel
    lateinit var adapter: UrlsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setSupportActionBar(toolbar)

        init()
        handleIntent(intent)
    }

    private fun init() {

        binding = DataBindingUtil.setContentView(this, R.layout.content_main)
        binding.executePendingBindings()

        viewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory(this))
            .get(UrlsListViewModel::class.java)
        binding.viewModel = viewModel

        setRecyclerView()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            AddFragment.newInstance()
        }
    }

    private fun setRecyclerView() {

        viewModel.urlsList.observe(this, Observer<List<ItemUrl>> { list ->
            list?.let {
                showEmptyList(it.isEmpty())
                adapter = UrlsListAdapter(it)
            }
        })

        binding.recyclerView.apply { this.adapter = adapter }
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
        adapter = UrlsListAdapter(results)
        /* val urlAddressList = arrayOfNulls<String>(results.size)
         for (i in 0 until results.size){
             urlAddressList[i] = results[i].name
         }

         val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, urlAddressList)*/
    }


    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
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
        const val NAMES_ASC = "sorting"
        const val NAMES_DESC = "Saved type of list sorting"
        const val AVAILABILITY = "Saved type of list sorting"
        const val RESPONSE_TIME_ASC = "Saved type of list sorting"
        const val RESPONSE_TIME_DESC = "Saved type of list sorting"
    }
}
