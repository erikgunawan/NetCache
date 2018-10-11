package id.ergun.netcache.configs

import id.ergun.netcache.models.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    fun getPost(): Observable<List<Post>>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Observable<Post>

}