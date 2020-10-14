package com.test.mavericstest.ui.movielist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.mavericstest.common.*
import com.test.mavericstest.data.models.MovieDetails
import com.test.mavericstest.data.models.MovieResponse
import com.test.mavericstest.data.models.Search
import com.test.mavericstest.data.repositry.DataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFetcherViewModel @Inject constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {
    private var mMovieList: MutableLiveData<ViewState<MovieResponse>> = MutableLiveData()
    private var mMovieDetails: MutableLiveData<ViewState<MovieDetails>> = MutableLiveData()

    private val selectedMovie = MutableLiveData<Search>()

    fun getMovieList(): MutableLiveData<ViewState<MovieResponse>> {
        return mMovieList
    }

    fun getMovieDetails(): MutableLiveData<ViewState<MovieDetails>> {
        return mMovieDetails
    }

    fun getSelectedMovie(): MutableLiveData<Search> {
        return selectedMovie
    }

    private fun onError(throwable: Throwable) {
        mMovieList.value = NetworkError(throwable.localizedMessage.toString())
    }


    fun fetchMovies(searchTerm: String, page: Int) {
        mMovieList.value = Loading
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        // viewModelScope launch the new coroutine on Main Dispatcher internally
        viewModelScope.launch(coroutineExceptionHandler) {
            val movie = dataRepository.getMoviesList(searchTerm,page)

            mMovieList.value = Success(movie)
        }
    }

    fun getSelectedMovieDetail(mImDbId: String) {
        mMovieDetails.value = Loading
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        // viewModelScope launch the new coroutine on Main Dispatcher internally
        viewModelScope.launch(coroutineExceptionHandler) {
            val movie = dataRepository.getMovieDetail(mImDbId)

            mMovieDetails.value = Success(movie)
        }
    }

}