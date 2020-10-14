package com.test.mavericstest.di

import android.app.Application
import com.test.mavericstest.di.annotations.ApplicationScope
import com.test.mavericstest.di.modules.ActivityBuilderModule
import com.test.mavericstest.di.modules.AppModule
import com.test.mavericstest.AppBase
import com.test.mavericstest.di.modules.FragmentBuildersModule
import com.test.mavericstest.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Main application component that connects all the dependency providers(modules) to application
 */
@ApplicationScope
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilderModule::class, FragmentBuildersModule::class, ViewModelModule::class]
)
interface AppComponent {
    fun inject(mApplication: AppBase)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}