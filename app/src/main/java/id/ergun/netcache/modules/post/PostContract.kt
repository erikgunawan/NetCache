package id.ergun.netcache.modules.post

import id.ergun.netcache.models.Post

class PostContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showData(posts: List<Post>, message: String = "")
        fun showError(error: String)
    }

    interface Presenter {
        fun getData()
        fun onAttach()
        fun onDetach()
    }

    interface Callback<T> {
        fun onResponse(data: T)
        fun onError(error: String)
    }
}