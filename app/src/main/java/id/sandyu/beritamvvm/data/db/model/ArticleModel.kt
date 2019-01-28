package id.sandyu.beritamvvm.data.db.model

data class ArticleModel(
    val source: SourceModel,
    val author: String? = "",
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String? = "",
    val publishedAt: String
)

data class SourceModel(
    val id: String? = "",
    val name: String
)