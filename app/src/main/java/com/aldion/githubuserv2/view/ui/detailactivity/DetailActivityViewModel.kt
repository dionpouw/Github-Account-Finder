package com.aldion.githubuserv2.view.ui.detailactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aldion.githubuserv2.data.api.ApiConfig
import com.aldion.githubuserv2.data.database.UserFav
import com.aldion.githubuserv2.data.model.DetailUserResponse
import com.aldion.githubuserv2.data.repository.UserFavRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel(application: Application) : ViewModel() {

    private val mUserFavRepository: UserFavRepository = UserFavRepository(application)

    private val _users = MutableLiveData<DetailUserResponse>()
    val users: LiveData<DetailUserResponse> = _users

    fun insert(userFav: UserFav) {
        mUserFavRepository.insert(userFav)
    }

    fun detailUser(login: String) {
        val client = ApiConfig.getApiService().getDetailUser(login)
        with(client) {
            enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _users.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}