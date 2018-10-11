package id.ergun.netcache.modules.post.detail

import id.ergun.netcache.models.Post

class PostDetailContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showData(posts: Post)
        fun showError(error: String)
    }

    interface Presenter {
        fun getData(id: Int)
        fun onAttach()
        fun onDetach()
    }

    interface Callback<T> {
        fun onResponse(data: T)
        fun onError(error: String)
    }
}