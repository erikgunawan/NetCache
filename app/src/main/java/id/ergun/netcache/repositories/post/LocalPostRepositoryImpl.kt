package id.ergun.netcache.repositories.post

import android.content.Context
import id.ergun.netcache.databases.database
import id.ergun.netcache.databases.tables.TPost
import id.ergun.netcache.databases.tables.TPost.Companion.BODY
import id.ergun.netcache.databases.tables.TPost.Companion.POST_ID
import id.ergun.netcache.databases.tables.TPost.Companion.TABLE_POST
import id.ergun.netcache.databases.tables.TPost.Companion.TITLE
import id.ergun.netcache.databases.tables.TPost.Companion.USER_ID
import id.ergun.netcache.models.Post
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class LocalPostRepositoryImpl(private val context: Context): LocalPostRepository {

    override fun getAllPost(): List<Post> {
        val posts : MutableList<Post> = mutableListOf()
        context.database.use {
            val result = select(TABLE_POST)
            val tposts = result.parseList(classParser<TPost>())

            for (tpost in tposts) {
                val post: id.ergun.netcache.models.Post
                        = Post(userId = tpost.userId, id = tpost.postId, title = tpost.title, body = tpost.body)
                posts.add(post)
            }
        }
        return posts
    }

    override fun insert(post: Post) {
        context.database.use {
            insert(TABLE_POST,
                    USER_ID to post.userId,
                    POST_ID to post.id,
                    TITLE to post.title,
                    BODY to post.body)
        }
    }

    override fun insertBatch(posts: List<Post>) {
        for (post in posts) {
            insert(post)
        }
    }

    override fun delete(id: Int) {
        context.database.use {
            delete(TABLE_POST, "($POST_ID = {id})",
                    "id" to id.toString())
        }
    }

    override fun deleteAll() {
        context.database.use {
            delete(TABLE_POST)
        }
    }
}