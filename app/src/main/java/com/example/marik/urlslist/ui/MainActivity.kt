package com.example.marik.urlslist.ui

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.marik.urlslist.Injector
import com.example.marik.urlslist.databinding.ContentMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ContentMainBinding
    lateinit var viewModel: UrlsListViewModel
    lateinit var adapter: UrlsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        init()
    }

    private fun init() {

        binding = DataBindingUtil.setContentView(this, R.layout.content_main)
        binding.executePendingBindings()

        viewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory(this))
            .get(UrlsListViewModel::class.java)

        fab.setOnClickListener {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            AddFragment.newInstance()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle item selection
        return when (item.itemId) {
            R.id.search -> {
                onSearchRequested()
                true
            }
            R.id.sortByNameAsc -> {
                viewModel.getByNameAsc()// hop! kayni! viewModelic stacacy pti
                true
            }
            R.id.sortByNameDesc -> {
                viewModel.getByNameDesc()
                true
            }
            R.id.sortByAvailability -> {
                viewModel.getAllByAvailability()
                true
            }
            R.id.sortByResponseTimeAsc -> {
                viewModel.getByResponseTimeAsc()
                true
            }
            R.id.sortByResponseTimeDesc -> {
                viewModel.getByResponseTimeDesc()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
