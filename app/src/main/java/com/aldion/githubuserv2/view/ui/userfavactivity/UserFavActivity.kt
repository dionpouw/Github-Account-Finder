package com.aldion.githubuserv2.view.ui.userfavactivity

import com.aldion.githubuserv2.ViewModelFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aldion.githubuserv2.data.database.UserFav
import com.aldion.githubuserv2.databinding.ActivityUserFavBinding
import com.aldion.githubuserv2.view.UsersAdapter

class UserFavActivity : AppCompatActivity() {
    private var _activityUserFavBinding: ActivityUserFavBinding? = null
    private val binding get() = _activityUserFavBinding
    private lateinit var userFavViewModel: UserFavViewModel
    private val listAdapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityUserFavBinding = ActivityUserFavBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Favourite User"

        userFavViewModel = obtainViewModel(this@UserFavActivity)

        binding?.recyclerUsers?.apply {
            layoutManager = LinearLayoutManager(this@UserFavActivity)
            setHasFixedSize(true)
            adapter = listAdapter
        }

        listAdapter.notifyDataSetChanged()

        userFavViewModel.getAllUsers().observe(this@UserFavActivity, {
            listAdapter.setList(it.map { it2 -> UserFav.userFavToItemItem(it2) })
        })

        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    return false // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    val position:Int = viewHolder.absoluteAdapterPosition
                    userFavViewModel.delete(listAdapter.getNoteAt(position))
                    Toast.makeText(this@UserFavActivity, "User Deleted", Toast.LENGTH_SHORT).show()
                }
            }).attachToRecyclerView(binding?.recyclerUsers)

    }


    private fun obtainViewModel(activity: AppCompatActivity): UserFavViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserFavViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityUserFavBinding = null
    }
}