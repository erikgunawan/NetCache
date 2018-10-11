package id.ergun.netcache.repositories.post

import android.util.Log
import id.ergun.netcache.configs.RetrofitClient
import id.ergun.netcache.models.Post
import id.ergun.netcache.modules.post.PostContract
import id.ergun.netcache.modules.post.detail.PostDetailContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostRepositoryImpl: PostRepository {

    private val apiService by lazy {
        RetrofitClient().create()
    }

    private var compositeDisposable = CompositeDisposable()

    override fun getAllPost(callback: PostContract.Callback<List<Post>>) {
        compositeDisposable.add(
                apiService.getPost()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                { posts ->
                                    callback.onResponse(posts)
                                },
                                {
                                    callback.onError("Terjadi kesalahan")
                                    Log.e("PostRepositoryImpl", "Error: " + it.message)
                                }
                        )
        )
    }

    override fun getPostById(id: Int, callback: PostDetailContract.Callback<Post>) {
        compositeDisposable.add(
                apiService.getPostById(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                { post ->
                                    callback.onResponse(post)
                                },
                                {
                                    callback.onError("Terjadi kesalahan")
                                    Log.e("PostRepositoryImpl", "Error: " + it.message)
                                }
                        )
        )
    }

    fun onAttach() {
        compositeDisposable = CompositeDisposable()
    }

    fun onDetach() {
        compositeDisposable.dispose()
    }
}