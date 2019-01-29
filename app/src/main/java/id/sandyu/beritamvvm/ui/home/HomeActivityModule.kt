package id.sandyu.beritamvvm.ui.home

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(
    HomeActivitySubcomponent::class
))
abstract class HomeActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(HomeActivity::class)
    abstract fun bindHomeActivityInjectorFactory(builde: HomeActivitySubcomponent.Builder):
            AndroidInjector.Factory<out Activity>
}