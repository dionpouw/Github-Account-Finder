package com.aldion.githubuserv2.view.ui.userfavactivity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aldion.githubuserv2.data.database.UserFav
import com.aldion.githubuserv2.data.repository.UserFavRepository

class UserFavViewModel(application: Application):ViewModel() {
    private val mUserFavRepository: UserFavRepository = UserFavRepository(application)

    fun getAllUsers():LiveData<List<UserFav>> = mUserFavRepository.getAllUsers()

    fun delete(userFav: UserFav) {
        mUserFavRepository.delete(userFav)
    }
}