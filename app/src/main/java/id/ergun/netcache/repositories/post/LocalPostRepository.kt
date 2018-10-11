package id.ergun.netcache.repositories.post

import id.ergun.netcache.models.Post

interface LocalPostRepository {

    fun getAllPost(): List<Post>
//
//    fun getPostById(id: Int, callback: PostDetailContract.Callback<Post>)
//
    fun insert(post: Post)
//
//    fun update(post: Post)

    fun insertBatch(posts: List<Post>)

    fun deleteAll()

    fun delete(id: Int)
}