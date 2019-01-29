package id.sandyu.beritamvvm.ui.home

import dagger.Subcomponent
import dagger.android.AndroidInjector
import id.sandyu.beritamvvm.ui.news.NewsListFragmentModule


@Subcomponent(modules = arrayOf(NewsListFragmentModule::class))
interface HomeActivitySubcomponent : AndroidInjector<HomeActivity>{
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeActivity>()
}