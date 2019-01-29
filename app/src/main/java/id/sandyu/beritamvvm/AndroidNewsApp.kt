package id.sandyu.beritamvvm

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import id.sandyu.beritamvvm.di.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

class AndroidNewsApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this)
           .build().inject(this)

        Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG)Timber.plant(Timber.DebugTree())
    }

}