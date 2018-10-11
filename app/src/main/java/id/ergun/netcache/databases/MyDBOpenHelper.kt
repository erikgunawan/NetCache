package id.ergun.netcache.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.ergun.netcache.databases.tables.TPost
import org.jetbrains.anko.db.*

class MyDBOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    companion object {
        private var instance: MyDBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDBOpenHelper {
            if (instance == null) {
                instance = MyDBOpenHelper(ctx.applicationContext)
            }
            return instance as MyDBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TPost.TABLE_POST, true,
                TPost.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TPost.POST_ID to INTEGER + UNIQUE,
                TPost.USER_ID to INTEGER,
                TPost.TITLE to TEXT,
                TPost.BODY to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TPost.TABLE_POST, true)
    }
}

val Context.database: MyDBOpenHelper
    get() = MyDBOpenHelper.getInstance(applicationContext)