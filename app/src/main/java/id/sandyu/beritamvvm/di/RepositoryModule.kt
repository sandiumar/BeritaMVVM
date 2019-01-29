package id.sandyu.beritamvvm.di

import android.content.Context
import dagger.Module
import dagger.Provides
import id.sandyu.beritamvvm.data.db.ArticleDao
import id.sandyu.beritamvvm.data.db.NewsServices
import id.sandyu.beritamvvm.data.db.repository.NewsRepository
import id.sandyu.beritamvvm.data.db.repository.NewsRepositoryImpl

@Module
class RepositoryModule {

    @Provides
    fun provideNewsRepo(newsServices: NewsServices,
                        articleDao: ArticleDao,
                        context: Context): NewsRepository = NewsRepositoryImpl(newsServices, articleDao, context)
}