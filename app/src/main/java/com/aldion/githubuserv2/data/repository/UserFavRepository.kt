package com.aldion.githubuserv2.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aldion.githubuserv2.data.database.UserFav
import com.aldion.githubuserv2.data.database.UserFavDao
import com.aldion.githubuserv2.data.database.UserFavRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserFavRepository(application: Application) {
    private val mUserFavDao: UserFavDao
    private val executorService: ExecutorService = Executors.newFixedThreadPool(2)

    init {
        val db = UserFavRoomDatabase.getDatabase(application)
        mUserFavDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<UserFav>> = mUserFavDao.getAllUsers()

    fun insert(userFav: UserFav) {
        executorService.execute { mUserFavDao.insert(userFav) }
    }

    fun delete(userFav: UserFav) {
        executorService.execute { mUserFavDao.delete(userFav) }
    }
}