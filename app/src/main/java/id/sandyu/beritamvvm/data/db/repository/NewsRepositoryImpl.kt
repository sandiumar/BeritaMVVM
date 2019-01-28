package id.sandyu.beritamvvm.data.db.repository

import android.content.Context
import com.github.ajalt.timberkt.d
import id.sandyu.beritamvvm.data.db.ArticleDao
import id.sandyu.beritamvvm.data.db.NewsServices
import id.sandyu.beritamvvm.domain.model.Article
import id.sandyu.beritamvvm.utils.isNetworkStatusAvailable
import io.reactivex.Single

class NewsRepositoryImpl(private val service: NewsServices,
                         private val articleDao: ArticleDao,
                         private val context: Context) : NewsRepository{

    override fun getTopNews(country: String, category: String): Single<List<Article>> {
        if (context.isNetworkStatusAvailable()) {
            return service.getTopHeadlines(country = country, category =category)
                .flattenAsFlowable { it.articles }
                .map {
                    val article = Article(
                        id = 0,
                        title = it.title,
                        author = it.author ?: "",
                        image = it.urlToImage ?: "",
                        publishedAt = it.publishedAt,
                        sourceId = it.source.id ?: "",
                        sourceName = it.source.name,
                        url = it.url
                    )
                    d {"insert article $(article.title)"}
                    articleDao.insert(article)
                    //return article
                    article
                }.toList()
        } else {
            return articleDao.getArticles()
        }
    }


    override fun getLocalNews(): Single<List<Article>> {
        return articleDao.getArticles()
    }

    private val articleDomainModeMapper: (Article) -> Article = { article ->
        Article(
            id = article.id,
            title = article.title,
            author = article.author,
            image = article.image,
            publishedAt = article.publishedAt,
            sourceId = article.sourceId,
            sourceName = article.sourceName,
            url = article.url
        )
    }
}