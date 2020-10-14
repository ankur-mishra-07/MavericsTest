package com.test.mavericstest.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.mavericstest.di.annotations.ApplicationScope
import com.test.mavericstest.di.annotations.ViewModelKey
import com.test.mavericstest.ui.movielist.viewmodel.MovieFetcherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Provides map of all ViewModels and a ViewModelFactory for dependencies
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieFetcherViewModel::class)
    abstract fun bindFetcherModel(mainViewModelUser: MovieFetcherViewModel): ViewModel

    @Binds
    @ApplicationScope
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}