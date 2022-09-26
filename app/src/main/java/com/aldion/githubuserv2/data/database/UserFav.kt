package com.aldion.githubuserv2.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldion.githubuserv2.data.model.ItemsItem
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserFav(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username:String = "username",

    @ColumnInfo(name = "profile_pict")
    var profilePict:String = "url"
) : Parcelable{
    companion object {
        fun userFavToItemItem(userFav: UserFav): ItemsItem {
            return ItemsItem(
                userFav.id, userFav.username, userFav.profilePict
            )
        }
    }
}


