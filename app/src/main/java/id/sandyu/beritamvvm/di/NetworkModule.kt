package id.sandyu.beritamvvm.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import id.sandyu.beritamvvm.data.db.NewsServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


const val  API_KEY = "011c03001c644b159cf4c3b1dc938879"

@Module
class NetworkModule {

    @Provides
    fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsServices::class.java)

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

    @Provides
    fun providesOkHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor({chain ->
                val request = chain.request().newBuilder().addHeader("X-Api-Key", API_KEY).build()
                chain.proceed(request)
            })
            .build()
    }

}