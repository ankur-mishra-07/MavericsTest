package com.test.mavericstest.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.mavericstest.R
import com.test.mavericstest.base.BaseActivity
import com.test.mavericstest.common.AppConstants
import com.test.mavericstest.data.models.Search
import com.test.mavericstest.ui.movielist.MovieDetails
import com.test.mavericstest.ui.movielist.MovieListFragment
import com.test.mavericstest.ui.movielist.viewmodel.MovieFetcherViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity() {
    private lateinit var viewModel: MovieFetcherViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
        defaultFragment(
            MovieListFragment.newInstance(AppConstants.ColumnCount),
            container = R.id.mainContainer
        )
    }

    private fun initListener() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieFetcherViewModel::class.java)
        viewModel.getSelectedMovie().observe(this, Observer { onMovieSelected(it) })
    }

    private fun onMovieSelected(search: Search?) {
        callFragmentSwitcher(MovieDetails.newInstance(search!!.imdbID), container = R.id.mainContainer)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }
}