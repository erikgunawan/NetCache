package id.ergun.netcache.modules.post.detail

import id.ergun.netcache.models.Post
import id.ergun.netcache.repositories.post.PostRepositoryImpl

class PostDetailPresenter(private val view: PostDetailContract.View,
                          private val repository: PostRepositoryImpl = PostRepositoryImpl()): PostDetailContract.Presenter {

    override fun getData(id: Int) {
        view.showLoading()
        val callback = object: PostDetailContract.Callback<Post> {
            override fun onResponse(post: Post) {
                view.showData(post)
                view.hideLoading()
            }

            override fun onError(error: String) {
                view.showError(error)
                view.hideLoading()
            }
        }

        repository.getPostById(id, callback)
    }

    override fun onAttach() {
        repository.onAttach()
    }

    override fun onDetach() {
        repository.onDetach()
    }
}