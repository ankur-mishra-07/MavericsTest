package com.test.mavericstest.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.test.mavericstest.R
import com.test.mavericstest.common.MyObserver
import com.test.mavericstest.ui.movielist.MovieListFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, LifecycleObserver {
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MyObserver())
    }

    protected fun defaultFragment(
        currentFragment: Fragment,
        container: Int
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(container, currentFragment, currentFragment.javaClass.simpleName)
            .commit()
    }


    protected fun callFragmentSwitcher(
        currentFragment: Fragment,
        container: Int
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(container, currentFragment, currentFragment.javaClass.simpleName)
            .addToBackStack(null)
            .commit()

    }

}