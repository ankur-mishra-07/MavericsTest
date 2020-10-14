package com.test.mavericstest.di.modules

import com.test.mavericstest.ui.movielist.MovieDetails
import com.test.mavericstest.ui.movielist.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetails

}