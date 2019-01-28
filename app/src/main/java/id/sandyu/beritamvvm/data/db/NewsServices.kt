package id.sandyu.beritamvvm.data.db

import id.sandyu.beritamvvm.data.db.model.response.GetNewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String,
                        @Query("category") category: String): Single<GetNewsResponse>

    @GET("everything")
    fun getEverything(@Query("q") query: String,
                      @Query("sortBy") sortBy: String) : Single<GetNewsResponse>
}