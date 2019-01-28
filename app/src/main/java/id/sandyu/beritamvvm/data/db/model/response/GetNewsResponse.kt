package id.sandyu.beritamvvm.data.db.model.response

import id.sandyu.beritamvvm.data.db.model.ArticleModel

data class GetNewsResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleModel>
)