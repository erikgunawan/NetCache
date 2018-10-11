package id.ergun.netcache.modules.post

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ergun.netcache.R
import id.ergun.netcache.models.Post
import kotlinx.android.synthetic.main.row_post.view.*

class PostAdapter(private val context: Context,
                  private var posts : List<Post>,
                  private val listener: (ItemClickListener))
    : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view = ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_post, parent, false))
        view.itemView.setOnClickListener {
            listener.onItemClick(it, view.adapterPosition)
        }
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun setData(posts: List<Post>) {
        this.posts = posts
    }

    fun getData(): List<Post> = posts

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(post: Post) {
            itemView.tv_title.text = post.title
        }
    }

    interface ItemClickListener {
        fun onItemClick(v: View, position: Int)
    }
}