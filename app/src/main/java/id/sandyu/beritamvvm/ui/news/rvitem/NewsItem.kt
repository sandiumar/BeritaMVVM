package id.sandyu.beritamvvm.ui.news.rvitem

import android.util.Log
import com.bumptech.glide.Glide
import id.sandyu.beritamvvm.R
import id.sandyu.beritamvvm.di.GlideApp
import id.sandyu.beritamvvm.domain.model.Article
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_news.view.*

interface NewsListener{
    fun onNewsClick(article: Article)
}

class NewsItem(private val article: Article,
                private val listener: NewsListener): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvTitle.text = article.title
        viewHolder.itemView.tvSource.text = article.sourceName

        Log.d("tag","url gambar -> ${article.image}")

        if (article.image.isNotEmpty()) {
            GlideApp.with(viewHolder.itemView.context)
                .load(article.image)
                .into(viewHolder.itemView.imgNews)
        }

        viewHolder.itemView.setOnClickListener{
            listener.onNewsClick(article)
        }
    }

    override fun getLayout(): Int = R.layout.item_news


}