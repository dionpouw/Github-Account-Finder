package com.aldion.githubuserv2.view.ui.listuseractivity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldion.githubuserv2.R
import com.aldion.githubuserv2.databinding.ActivityListUserBinding
import com.aldion.githubuserv2.view.UsersAdapter
import com.aldion.githubuserv2.view.ui.settingactivity.SettingActivity
import com.aldion.githubuserv2.view.ui.userfavactivity.UserFavActivity

class ListUserActivity : AppCompatActivity() {

    private var _activitylistuserbinding: ActivityListUserBinding? = null
    private val binding get() = _activitylistuserbinding
    private val listAdapter = UsersAdapter()
    private val listUserViewModel by viewModels<ListUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitylistuserbinding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.recyclerUsers?.apply {
            layoutManager = LinearLayoutManager(this@ListUserActivity)
            setHasFixedSize(true)
            adapter = listAdapter

        }
        listAdapter.notifyDataSetChanged()

        listUserViewModel.isLoading.observe(this, {
            showLoading(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                listUserViewModel.findUser(query)
                listUserViewModel.searchUsers.observe(this@ListUserActivity, {
                    if (it.isEmpty()) {
                        Toast.makeText(
                            this@ListUserActivity,
                            "Data gagal didapatkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding?.tvEmpty?.visibility = View.VISIBLE
                    } else {
                        binding?.tvEmpty?.visibility = View.GONE
                        listAdapter.setList(it)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav -> {
                val i = Intent(this, UserFavActivity::class.java)
                startActivity(i)
                true
            }
            R.id.setting ->{
                val i = Intent(this,SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activitylistuserbinding = null
    }
}