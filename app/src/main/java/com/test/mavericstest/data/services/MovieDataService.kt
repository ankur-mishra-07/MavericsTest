package com.test.mavericstest.data.services

import com.test.mavericstest.data.models.MovieDetails
import com.test.mavericstest.data.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDataService {

    @GET("?apikey=b9bd48a6")
    suspend fun getMovieList(
        @Query("s") searchTerm1: String,
        @Query("type") type: String,
        @Query("p") i: Int
    ): MovieResponse

    @GET("?apikey=b9bd48a6")
    suspend fun getMovieDetail(@Query("i") mImDbId: String): MovieDetails

}