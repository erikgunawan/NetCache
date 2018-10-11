package id.ergun.netcache.databases.tables

data class TPost(
        val id: Long,
        val postId: Int,
        val userId: Int,
        val title: String,
        val body: String
) {
    companion object {
        const val TABLE_POST: String = "T_POST"
        const val ID: String = "ID_"
        const val POST_ID: String = "POST_ID"
        const val USER_ID: String = "USER_ID"
        const val TITLE: String = "TITLE"
        const val BODY: String = "BODY"
    }
}