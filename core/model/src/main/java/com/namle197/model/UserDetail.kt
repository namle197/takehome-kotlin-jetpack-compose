package com.namle197.model

import com.google.gson.annotations.SerializedName

// User detail model
data class UserDetail(
    @SerializedName(value = "login")
    val login: String, // login Login username

    @SerializedName(value = "avatar_url")
    val avatarUrl: String, // avatar_url Avatar url of the user

    @SerializedName(value = "html_url")
    val htmlUrl: String, // html_url The landing page url of the user

    @SerializedName(value = "location")
    val location: String?, // location The living city/country of the user

    @SerializedName(value = "followers")
    val followers: Int, // followers The number of following users

    @SerializedName(value = "following")
    val following: Int // following The number of followed users
)
