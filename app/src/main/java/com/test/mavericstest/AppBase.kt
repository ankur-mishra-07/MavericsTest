package com.test.mavericstest

import android.app.Activity
import androidx.multidex.MultiDexApplication
import com.test.mavericstest.di.AppComponent
import com.test.mavericstest.di.DaggerAppComponent
import com.test.mavericstest.di.modules.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class AppBase : MultiDexApplication(), HasActivityInjector {
    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        AppInjector.init(this)
    }

}