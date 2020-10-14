package  com.test.mavericstest.di.modules

import android.app.Application
import android.content.Context
import com.test.mavericstest.di.annotations.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * App level dependencies
 */
@Module(includes = [NetworkServiceModule::class])
object AppModule {
    @JvmStatic
    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context = application
}