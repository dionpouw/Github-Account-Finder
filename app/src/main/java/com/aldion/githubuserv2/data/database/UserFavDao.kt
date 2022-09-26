package com.aldion.githubuserv2.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun insert(userFav:UserFav)

    @Delete
    fun delete(userFav:UserFav)

    @Query("Select * from UserFav ORDER BY username ASC")
    fun getAllUsers(): LiveData<List<UserFav>>
}