package id.ergun.netcache.modules.post

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import id.ergun.netcache.R
import id.ergun.netcache.R.id.search_post
import id.ergun.netcache.models.Post
import id.ergun.netcache.models.Post.Companion.POST
import id.ergun.netcache.modules.post.detail.PostDetailActivity
import id.ergun.netcache.utils.gone
import id.ergun.netcache.utils.invisible
import id.ergun.netcache.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PostActivity: AppCompatActivity(), PostContract.View,
        PostAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private var posts: MutableList<Post> = mutableListOf()

    private lateinit var adapter: PostAdapter

    private lateinit var presenter: PostPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = PostPresenter(this,this)
        presenter.onAttach()

        adapter = PostAdapter(this, posts, this)

        rv_data.layoutManager = LinearLayoutManager(this)
        rv_data.adapter = adapter

        swipe_refresh.setOnRefreshListener(this)

        presenter.getLocalData()
        presenter.getData()
    }

    override fun showData(posts: List<Post>, message: String) {
        this.posts.clear()
        this.posts.addAll(posts)
        adapter.notifyDataSetChanged()
        if (message.isNotEmpty()) toast(message)
    }

    override fun showError(error: String) {
        toast(error)
    }

    override fun showLoading() {
        progressbar.visible()
        if (this.posts.size==0) {
            layout_empty.visible()
            rv_data.gone()
        }
        else {
            layout_empty.gone()
        }
    }

    override fun hideLoading() {
        progressbar.invisible()
        if (this.posts.size==0) {
            layout_empty.visible()
        }
        else {
            layout_empty.gone()
            rv_data.visible()
        }
    }

    override fun onItemClick(v: View, position: Int) {
        startActivity<PostDetailActivity>(POST to adapter.getData()[position])
    }

    override fun onRefresh() {
        swipe_refresh.isRefreshing = false
        presenter.getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post, menu)

        val searchView = menu?.findItem(search_post)?.actionView as android.widget.SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filterPosts: List<Post> = posts.filter { s -> s.title.contains(newText, true) }
                    if (newText.isNotEmpty()) {
                        adapter.setData(filterPosts)
                        adapter.notifyDataSetChanged()
                    }
                    else {
                        adapter.setData(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
                else {
                    adapter.setData(posts)
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })
        return true
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}