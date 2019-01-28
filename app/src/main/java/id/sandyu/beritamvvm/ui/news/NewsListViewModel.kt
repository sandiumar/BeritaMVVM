package id.sandyu.beritamvvm.ui.news

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import id.sandyu.beritamvvm.data.db.repository.NewsRepository
import id.sandyu.beritamvvm.domain.model.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


private val TAG = NewsListViewModel::class.java.name

class NewsListViewModel @Inject constructor(private val repo: NewsRepository) : ViewModel() {

    //declare state for news list
    val stateLiveData = MutableLiveData<NewsListState>()

    //initiate state for news list
    init {
        stateLiveData.value = LoadingState(emptyList(), false)
    }

    fun updateNewsList(){
        d{"update news list"}
        getNewsList()
    }

    fun refreshNewsList() {
        stateLiveData.value = LoadingState(emptyList(), false)
        getNewsList()
    }

    fun restoreNewsList(){
        d{"restore news list"}
        stateLiveData.value = DefaultState(obtainCurrentData(), true)
    }

    private fun getNewsList() {
        repo.getTopNews(country = "us", category = "technology")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onNewsReceived, this::onError)
    }

    private fun onError(error: Throwable) {
        e{"error ${error.localizedMessage}"}
        stateLiveData.value = ErrorState(error.localizedMessage, obtainCurrentData(),false)
    }

    private fun onNewsReceived(news: List<Article>) {
        d{"data received ${news.size}"}
        val currentNews = obtainCurrentData().toMutableList()
        currentNews.addAll(news)
        d{"current news size ${currentNews.size}"}
        stateLiveData.value = DefaultState(currentNews, true)
    }

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false
}