package id.ergun.netcache.repositories.post

import id.ergun.netcache.models.Post
import id.ergun.netcache.modules.post.PostContract
import id.ergun.netcache.modules.post.detail.PostDetailContract

interface PostRepository {

    fun getAllPost(callback: PostContract.Callback<List<Post>>)

    fun getPostById(id: Int, callback: PostDetailContract.Callback<Post>)
}