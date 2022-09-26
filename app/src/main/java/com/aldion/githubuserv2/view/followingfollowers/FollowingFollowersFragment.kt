package com.aldion.githubuserv2.view.followingfollowers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldion.githubuserv2.data.model.ItemsItem
import com.aldion.githubuserv2.databinding.MainFragmentBinding
import com.aldion.githubuserv2.view.UsersAdapter

class FollowingFollowersFragment : Fragment() {

    private var binding: MainFragmentBinding? = null
    private val listAdapter = UsersAdapter()
    private var contextThis: Context? = null
    private val followingFollowersViewModel by viewModels<FollowingFollowersFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = arguments?.getString(ARG_USERNAME).toString()


        if (arguments?.getInt(ARG_SECTION_NUMBER, 0) == 0) {
            followingFollowersViewModel.findFollowing(userName)
        } else {
            followingFollowersViewModel.findFollowers(userName)
        }
        showLoading(true)
        followingFollowersViewModel.listFollowersFollowings.observe(viewLifecycleOwner, {
            listAdapter.setList(it.map { it2 -> ItemsItem.followersToItemsItem(it2) })
            showLoading(false)
        })

        binding!!.recyclerUsersFragment.apply {
            layoutManager = LinearLayoutManager(contextThis)
            setHasFixedSize(true)
            adapter = listAdapter
        }
        listAdapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contextThis = context
    }

    override fun onDetach() {
        super.onDetach()
        this.contextThis = null
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding!!.progressBar.visibility = View.VISIBLE
        } else {
            binding!!.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER =
            "com.aldion.githubuserv2.view.followingfollowers.SECTION_NUMBER"
        private const val ARG_USERNAME = "com.aldion.githubuserv2.view.followingfollowers.USERNAME"

        @JvmStatic
        fun newInstance(index: Int, userName: String) =
            FollowingFollowersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(ARG_USERNAME, userName)
                }
            }
    }
}
