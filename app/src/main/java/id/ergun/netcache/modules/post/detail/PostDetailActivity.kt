package id.ergun.netcache.modules.post.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import id.ergun.netcache.R
import id.ergun.netcache.models.Post
import id.ergun.netcache.models.Post.Companion.POST
import id.ergun.netcache.utils.invisible
import id.ergun.netcache.utils.visible
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity: AppCompatActivity(), PostDetailContract.View {

    private lateinit var post: Post

    private lateinit var presenter: PostDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        post = intent.getParcelableExtra(POST)
        fillView(post)

        presenter = PostDetailPresenter(this)
        presenter.onAttach()
        presenter.getData(post.id)
    }

    private fun fillView(post: Post) {
        tv_title.text = post.title
        tv_body.text = post.body
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun showData(post: Post) {
        fillView(post)
    }

    override fun showError(error: String) {
//        toast(error)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}