package com.aldion.githubuserv2.view.ui.detailactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aldion.githubuserv2.view.followingfollowers.FollowingFollowersFragment

class SectionsPagerAdapter(
    activity: AppCompatActivity,
    private val userName: String
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FollowingFollowersFragment.newInstance(position, userName)
    }

    override fun getItemCount(): Int = DetailActivity.TAB_TITLES.size
}