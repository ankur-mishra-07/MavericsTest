package com.test.mavericstest.di.modules

import com.test.mavericstest.di.annotations.ActivityScope
import com.test.mavericstest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Contains all activities to be bound to application dependency graph
 */
@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMainActivity(): MainActivity

}