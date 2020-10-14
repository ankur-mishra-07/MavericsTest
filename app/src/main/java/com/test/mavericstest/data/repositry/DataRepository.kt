package com.test.mavericstest.data.repositry

import com.test.mavericstest.common.AppConstants
import com.test.mavericstest.data.models.MovieDetails
import com.test.mavericstest.data.models.MovieResponse
import com.test.mavericstest.data.services.MovieDataService
import javax.inject.Inject

class DataRepository @Inject constructor(private val mService: MovieDataService) {
    suspend fun getMoviesList(searchTerm: String, page: Int): MovieResponse = mService.getMovieList(searchTerm, AppConstants.Type, page)
    suspend fun getMovieDetail(mImDbId: String): MovieDetails = mService.getMovieDetail(mImDbId)

}