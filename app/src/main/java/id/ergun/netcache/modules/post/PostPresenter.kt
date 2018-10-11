package id.ergun.netcache.modules.post

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import id.ergun.netcache.models.Post
import id.ergun.netcache.repositories.post.LocalPostRepositoryImpl
import id.ergun.netcache.repositories.post.PostRepositoryImpl
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PostPresenter(private val context: Context,
                    private val view: PostContract.View,
                    private val repository: PostRepositoryImpl = PostRepositoryImpl(),
                    private val localRepository: LocalPostRepositoryImpl = LocalPostRepositoryImpl(context)): PostContract.Presenter {

    override fun getData() {
        view.showLoading()
        val callback = object: PostContract.Callback<List<Post>> {
            override fun onResponse(data: List<Post>) {
                val sortedList = data.sortedWith(compareBy { it.title })
                view.showData(sortedList, "Data telah diperbarui")
                view.hideLoading()
                saveToLocal(data)
            }

            override fun onError(error: String) {
                view.showError(error)
                view.hideLoading()

                getLocalData()
            }
        }
        repository.getAllPost(callback)
    }

    fun getLocalData() {
        val data = localRepository.getAllPost()
        Log.d("data", Gson().toJson(data))
        val sortedList = data.sortedWith(compareBy { it.title })
        view.showData(sortedList)
    }

    fun saveToLocal(posts: List<Post>) {
        async(UI) {
            bg {
                localRepository.deleteAll()
                localRepository.insertBatch(posts)
            }.await()
        }
    }

    override fun onAttach() {
        repository.onAttach()
    }

    override fun onDetach() {
        repository.onDetach()
    }
}