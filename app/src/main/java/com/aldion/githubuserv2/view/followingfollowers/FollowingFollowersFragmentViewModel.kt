package com.aldion.githubuserv2.view.followingfollowers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aldion.githubuserv2.data.api.ApiConfig
import com.aldion.githubuserv2.data.model.FollowingFollowersResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFollowersFragmentViewModel : ViewModel() {
    private val _listFollowersFollowings = MutableLiveData<List<FollowingFollowersResponseItem>>()

    val listFollowersFollowings: LiveData<List<FollowingFollowersResponseItem>> =
        _listFollowersFollowings

    fun findFollowers(login: String) {
        val client = ApiConfig.getApiService().getFollowersUser(login)
        with(client) {
            enqueue(object : Callback<List<FollowingFollowersResponseItem>> {

                override fun onResponse(
                    call: Call<List<FollowingFollowersResponseItem>>,
                    response: Response<List<FollowingFollowersResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _listFollowersFollowings.value = response.body()
                    } else {
                        Log.e(
                            TAG,
                            "onFailure: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<List<FollowingFollowersResponseItem>>,
                    t: Throwable
                ) {
                    Log.e(TAG,
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
        }
    }

    fun findFollowing(login: String) {
        val client = ApiConfig.getApiService().getFollowingUser(login)
        with(client) {
            enqueue(object : Callback<List<FollowingFollowersResponseItem>> {

                override fun onResponse(
                    call: Call<List<FollowingFollowersResponseItem>>,
                    response: Response<List<FollowingFollowersResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _listFollowersFollowings.value = response.body()
                    } else {
                        Log.e(
                           TAG,
                            "onFailure: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<List<FollowingFollowersResponseItem>>,
                    t: Throwable
                ) {
                    Log.e(
                        TAG,
                        "onFailure: ${t.message.toString()}"
                    )
                }
            })
        }
    }

    companion object {
        private const val TAG = "FollowingFollowersFragmentViewModel"
    }
}