package id.ergun.netcache.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
        @SerializedName("userId")
        val userId: Int,

        @SerializedName("id")
        val id: Int,

        @SerializedName("title")
        val title: String,

        @SerializedName("body")
        val body: String
): Parcelable {

    companion object {
        const val POST = "post"
    }
}