package com.namle197.network.retrofit

import com.namle197.network.MobileTakeHomeDataSource
import com.namle197.model.User
import com.namle197.model.UserDetail
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitMobileTakeHomeNetwork @Inject constructor(okhttpCallFactory: Call.Factory) :
    MobileTakeHomeDataSource {
    companion object {
        private const val BASE_URL = "https://api.github.com"
    }

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitMobileTakeHomeApi::class.java)

    /**
     * Method to request users by page (pagination)
     *
     * @param perPage number of users per page
     * @param since get users since this user id
     *
     * @return list of users
     */
    override suspend fun getUsersByPage(perPage: Int, since: Int): List<User> = networkApi.getUsers(perPage, since)

    /**
     * Method to request user detail by id
     *
     * @param loginUserName user login name
     *
     * @return user detail
     */
    override suspend fun getUserDetail(loginUserName: String): UserDetail = networkApi.getUserDetail(loginUserName)
}