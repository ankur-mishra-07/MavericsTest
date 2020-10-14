package com.test.mavericstest.data.models
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

@Keep
data class MovieResponse(
    @SerializedName("Response")
    var response: String = "",
    @SerializedName("Search")
    var search: List<Search> = listOf(),
    @SerializedName("totalResults")
    var totalResults: String = ""
)

@Keep
data class Search(
    @SerializedName("imdbID")
    var imdbID: String = "",
    @SerializedName("Poster")
    var poster: String = "",
    @SerializedName("Title")
    var title: String = "",
    @SerializedName("Type")
    var type: String = "",
    @SerializedName("Year")
    var year: String = ""
)