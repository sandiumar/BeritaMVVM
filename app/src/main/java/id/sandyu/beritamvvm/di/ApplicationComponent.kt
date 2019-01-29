package id.sandyu.beritamvvm.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import id.sandyu.beritamvvm.AndroidNewsApp
import id.sandyu.beritamvvm.ui.home.HomeActivityModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelFactoryModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    HomeActivityModule::class
))


interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: AndroidNewsApp)
}