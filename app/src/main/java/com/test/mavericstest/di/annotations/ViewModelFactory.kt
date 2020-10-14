package com.test.mavericstest.di.annotations

import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Singleton
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelFactory