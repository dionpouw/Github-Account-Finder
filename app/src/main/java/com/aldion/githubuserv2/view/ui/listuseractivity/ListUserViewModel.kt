package com.aldion.githubuserv2.view.ui.listuseractivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aldion.githubuserv2.data.api.ApiConfig
import com.aldion.githubuserv2.data.model.ItemsItem
import com.aldion.githubuserv2.data.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserViewModel :ViewModel() {

    private val _searchUsers = MutableLiveData<List<ItemsItem>>()
    val searchUsers: LiveData<List<ItemsItem>> = _searchUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(login)
        with(client) {
            enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _searchUsers.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "FollowingFollowersFragmentViewModel"
    }
}