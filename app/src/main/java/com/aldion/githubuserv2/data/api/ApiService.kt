package com.aldion.githubuserv2.data.api

import com.aldion.githubuserv2.BuildConfig
import com.aldion.githubuserv2.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(
        @Query("q") login: String
    ): Call<SearchResponse>

    @GET("/users/{login}")
    @Headers("Authorization: token  ${BuildConfig.API_KEY}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("/users/{login}/following")
    @Headers("Authorization: token  ${BuildConfig.API_KEY}")
    fun getFollowingUser(
        @Path("login") login: String
    ): Call<List<FollowingFollowersResponseItem>>

    @GET("/users/{login}/followers")
    @Headers("Authorization: token  ${BuildConfig.API_KEY}")
    fun getFollowersUser(
        @Path("login") login: String
    ): Call<List<FollowingFollowersResponseItem>>
}
