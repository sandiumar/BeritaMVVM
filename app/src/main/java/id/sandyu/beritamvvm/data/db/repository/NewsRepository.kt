package id.sandyu.beritamvvm.data.db.repository

import id.sandyu.beritamvvm.domain.model.Article
import io.reactivex.Single

interface NewsRepository {
    fun getTopNews(country: String, category: String): Single<List<Article>>

    fun getLocalNews() : Single<List<Article>>
}