package com.zengcheng.getyourshittogether.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zengcheng.getyourshittogether.database.dao.HouseDao
import com.zengcheng.getyourshittogether.database.dao.MessageDao
import com.zengcheng.getyourshittogether.database.dao.UserDao
import com.zengcheng.getyourshittogether.database.entity.HouseEntity
import com.zengcheng.getyourshittogether.database.entity.MessageEntity
import com.zengcheng.getyourshittogether.database.entity.UserEntity

@Database(entities = [HouseEntity::class, UserEntity::class,MessageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun houseDao(): HouseDao
    abstract fun userDao(): UserDao?
    abstract fun messageDao(): MessageDao?

    companion object {
        private const val DB_NAME = "myhouse"
        private var sInstance: AppDatabase? = null
        fun init(context: Context) {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DB_NAME
                        ).allowMainThreadQueries().build()
                    }
                }
            }

        }

        val instance: AppDatabase?
            get() {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        throw NullPointerException("database == null")
                    }
                }
                return sInstance
            }
    }
}