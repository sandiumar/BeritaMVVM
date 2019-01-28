package id.sandyu.beritamvvm.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Module
abstract class ViewModelModule {
}