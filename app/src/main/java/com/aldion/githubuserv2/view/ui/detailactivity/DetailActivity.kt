package com.aldion.githubuserv2.view.ui.detailactivity

import com.aldion.githubuserv2.ViewModelFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.aldion.githubuserv2.R
import com.aldion.githubuserv2.data.database.UserFav
import com.aldion.githubuserv2.data.model.ItemsItem
import com.aldion.githubuserv2.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel = obtainViewModel(this@DetailActivity)


        val username = intent.getParcelableExtra<ItemsItem>(KEY)

        detailViewModel.detailUser(username!!.login)
        showLoading(true)
        detailViewModel.users.observe(this@DetailActivity, { detailResponse ->
            binding.apply {
                itemDetailName.text = detailResponse.name
                Glide.with(this@DetailActivity)
                    .load(detailResponse.avatarUrl).thumbnail(0.9f)
                    .into(itemDetailAvatar)
                itemDetailRepository.text = detailResponse.public_repos
                itemDetailUsername.text = detailResponse.login
                itemDetailFollowing.text = detailResponse.following.toString()
                itemDetailFollowers.text = detailResponse.followers.toString()
                itemDetailCompany.text = detailResponse.company
                itemDetailLocation.text = detailResponse.location

                showLoading(false)
            }
        })

        binding.btnAddFav.setOnClickListener {
            try {
                detailViewModel.insert(
                    UserFav(
                        username.id,
                        username.login,
                        username.avatarUrl
                    )
                )
                Toast.makeText(this@DetailActivity, "Data sudah ditambahkan", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this@DetailActivity, "Data sudah ada", Toast.LENGTH_SHORT).show()
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username.login)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailActivityViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailActivityViewModel::class.java)
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val KEY = "USERS"
    }
}