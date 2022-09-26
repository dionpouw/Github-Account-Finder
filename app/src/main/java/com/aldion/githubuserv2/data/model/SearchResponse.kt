package com.aldion.githubuserv2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchResponse(

    @field:SerializedName("items")
    val items: List<ItemsItem>
)

@Parcelize
data class ItemsItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String


) : Parcelable {

    companion object {
        fun followersToItemsItem(followers: FollowingFollowersResponseItem): ItemsItem {
            return ItemsItem(
                followers.id, followers.login, followers.avatarUrl
            )
        }
    }
}
