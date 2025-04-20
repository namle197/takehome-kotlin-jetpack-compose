package com.namle197.network.retrofit

import com.namle197.model.User
import com.namle197.model.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitMobileTakeHomeApi {
    @GET(value = "users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int
    ): List<User>

    @GET(value = "users/{id}")
    suspend fun getUserDetail(
        @Path("id") id: Int
    ): UserDetail
}