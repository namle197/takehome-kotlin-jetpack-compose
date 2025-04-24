package com.namle197.model

import com.google.gson.annotations.SerializedName

// User model
data class User(
    @SerializedName(value = "id")
    val id: Int, // id The user id

    @SerializedName(value = "login")
    val login: String, // login Login username

    @SerializedName(value = "avatar_url")
    val avatarUrl: String, // avatar_url Avatar url of a user

    @SerializedName(value = "html_url")
    val htmlUrl: String // html_url The landing page url of a user
)
