package dev.janssenbatista.githubsearch.models

import com.google.gson.annotations.SerializedName

data class Repository(
    val name: String,
    val description: String?,
    @SerializedName(value = "html_url")
    val htmlUrl: String
)

