package id.sandyu.beritamvvm.ui.news


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.support.AndroidSupportInjection
import id.sandyu.beritamvvm.R
import id.sandyu.beritamvvm.domain.model.Article
import id.sandyu.beritamvvm.ui.news.rvitem.NewsItem
import id.sandyu.beritamvvm.ui.news.rvitem.NewsListener
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject


private val TAG = NewsListFragment::class.java.name

class NewsListFragment : Fragment(), NewsListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewsListViewModel
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private var isLoading = false

    companion object {
        fun newInstance() = NewsListFragment()
    }

    //state observer
    private val stateObserver = Observer<NewsListState> { state ->
        state?.let {
            when (state) {
                is DefaultState -> {
                    isLoading = false
                    loading.isRefreshing = false

                    it.data.map {
                        Log.d(TAG, "data -> ${it.title} ${it.sourceName}")
                    }

                    // add data to adapter
                    it.data.map {
                        Section().apply {
                            add(NewsItem(it, this@NewsListFragment))
                            groupAdapter.add(this)
                        }
                    }
                }
                is LoadingState -> {
                    Log.d(TAG, "loading state")
                    loading.isRefreshing = true
                    isLoading = true
                }

                is ErrorState -> {
                    Log.e(TAG, "error state")
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)

        setupRv()
        loading.setOnRefreshListener(this)

        observerViewModel()

        savedInstanceState?.let {
            viewModel.restoreNewsList()
        } ?: viewModel.updateNewsList()
    }

    private fun setupRv() {
        rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupAdapter
        }
    }

    private fun observerViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stateLiveData.removeObserver(stateObserver)
    }

    override fun onNewsClick(article: Article) {
        Toast.makeText(activity, article.title, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        groupAdapter.clear()
        viewModel.refreshNewsList()
    }

}



